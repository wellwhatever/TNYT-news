package com.example.news.android

import androidx.lifecycle.ViewModel
import com.example.news.shared.core.common.stateInVmWithInitial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel(), MainActivityActions {
    private val showSnackbarFlow = MutableStateFlow(false)

    val state: StateFlow<MainActivityState> = showSnackbarFlow.map {
        MainActivityState(it)
    }.stateInVmWithInitial(MainActivityState(false))

    override fun showSnackbar() {
        showSnackbarFlow.value = true
    }

    override fun hideSnackbar() {
        showSnackbarFlow.value = false
    }
}

data class MainActivityState(
    val showSnackbar: Boolean,
)
