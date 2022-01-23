package com.example.core_mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun <S : MviState, I : MviIntent, E : MviEffect> MviComposable(
    viewModel: MviViewModel<S, I, E>,
    emitEffect: (E) -> Unit,
    content: @Composable (S, (I) -> Unit) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.observeEffects { effect ->
            emitEffect(effect)
        }
    }

    val sendIntent: (I) -> Unit = { intent: I ->
        viewModel.postIntent(intent)
    }
    val uiState = viewModel.state.collectAsState().value
    content.invoke(uiState, sendIntent)
}