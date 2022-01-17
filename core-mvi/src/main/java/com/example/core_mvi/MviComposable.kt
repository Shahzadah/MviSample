package com.example.core_mvi

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun <S : MviState, I : MviIntent, E : MviEffect> MviComposable(
    viewModel: MviViewModel<S, I, E>,
    emitEffect: (E) -> Unit,
    content: @Composable (S, (I) -> Unit) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    lifecycleOwner.lifecycleScope.launch {
        viewModel.observeEffects { effect ->
            emitEffect(effect)
        }
    }

    val uiState = viewModel.state.collectAsState().value
    content.invoke(uiState, viewModel::postIntent)
}