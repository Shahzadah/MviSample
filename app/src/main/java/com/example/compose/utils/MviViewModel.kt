package com.example.compose.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<S : MviState, I : MviIntent, E : MviEffect> : ViewModel() {

    private val intents: Channel<I> = Channel(Channel.UNLIMITED)

    abstract val defaultState: S

    private val _state by lazy { MutableStateFlow(defaultState) }
    val state: StateFlow<S> by lazy { _state }

    init {
        subscribeToIntents()
    }

    private val effects: Channel<E> =
        Channel(EFFECTS_BUFFER_SIZE, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private var collected = false

    abstract fun receiveIntent(intent: I)

    private suspend fun postSideEffect(effect: E) {
        effects.send(effect)
    }

    fun postEffect(effect: E) {
        viewModelScope.launch {
            postSideEffect(effect)
        }
    }

    fun postIntent(intent: I) {
        viewModelScope.launch {
            intents.send(intent)
        }
    }

    suspend fun observeEffects(block: suspend (value: E) -> Unit) {
        effects.receiveAsFlow().filterNotNull().collect(block)
    }

    fun reduceState(reducer: S.() -> S) {
        updateState(_state.value.reducer())
    }

    private fun updateState(newState: S) {
        _state.value = newState
    }

    private fun subscribeToIntents() = viewModelScope.launch {
        intents.consumeAsFlow().collect { intent ->
            receiveIntent(intent)
        }
    }

    companion object {
        private const val EFFECTS_BUFFER_SIZE = 5
    }
}
