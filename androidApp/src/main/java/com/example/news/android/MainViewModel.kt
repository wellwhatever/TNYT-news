package com.example.news.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel(), MainActivityActions {
    private val showSnackbarFlow = MutableStateFlow(false)

    val state: StateFlow<MainActivityState> = showSnackbarFlow.map {
        MainActivityState(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = MainActivityState(false)
    )

    override fun showSnackbar() {
        showSnackbarFlow.value = true
    }

    override fun hideSnackbar() {
        showSnackbarFlow.value = false
    }
}

data class MainActivityState(
    val showSnackbar: Boolean
)