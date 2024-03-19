package com.example.news.shared.core.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface NavigatorScope {
    val navigationScope: CoroutineScope
}

// TODO consider renaming it!
class NavigationDelegate<T> {
    private val channel = Channel<T>(Channel.CONFLATED)
    private val flow = channel.receiveAsFlow()

    context (NavigatorScope)
    operator fun invoke(value: T) {
        navigationScope.launch {
            channel.send(value)
        }
    }

    @Composable
    fun CollectNavigationEvent(action: (T) -> Unit) {
        LaunchedEffect(Unit) {
            flow.collect(action)
        }
    }
}
