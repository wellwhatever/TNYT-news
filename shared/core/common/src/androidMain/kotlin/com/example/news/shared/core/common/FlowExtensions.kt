package com.example.news.shared.core.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

context(ViewModel)
fun <T> Flow<T>.stateInVmWithInitial(initial: T) = stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
    initial,
)
