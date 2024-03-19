package com.example.news.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.news.android.navigation.AppNavGraph
import com.example.news.android.theme.TNYTTheme
import com.ramcosta.composedestinations.DestinationsNavHost

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TNYTTheme {
                TNYTApp()
            }
        }
    }
}

@Composable
fun TNYTApp(
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            },
        ) {
            DestinationsNavHost(
                modifier = Modifier.padding(it),
                navGraph = AppNavGraph,
            )
        }
    }
}
