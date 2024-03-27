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
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.news.android.navigation.AppNavGraph
import com.example.news.android.theme.TNYTTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine
import org.koin.androidx.compose.koinViewModel

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

@ExperimentalMaterial3Api
@Composable
fun TNYTApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val activityState = viewModel.state.collectAsStateWithLifecycle()

    TNYTTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            TNYTAppInternal(
                activityState = activityState.value,
                showSnackbar = viewModel::showSnackbar,
                hideSnackbar = viewModel::hideSnackbar
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TNYTAppInternal(
    modifier: Modifier = Modifier,
    activityState: MainActivityState,
    showSnackbar: () -> Unit,
    hideSnackbar: () -> Unit,
    engine: NavHostEngine = rememberNavHostEngine()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = engine.rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val appState = rememberAppState(
        currentDestination = backStackEntry.value?.destination,
    )
    val notImplementedMessage = stringResource(id = R.string.not_implemented_general)

    LaunchedEffect(activityState.showSnackbar) {
        if (activityState.showSnackbar) {
            val result = snackbarHostState.showSnackbar(
                message = notImplementedMessage,
                withDismissAction = true
            )
            if (result == SnackbarResult.Dismissed) {
                hideSnackbar()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            NewsTopAppBar(
                appState = appState,
                navigateBack = navController::popBackStack,
                onMoreClick = showSnackbar
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) {
        DestinationsNavHost(
            modifier = Modifier.padding(it),
            navGraph = AppNavGraph,
            engine = engine,
            navController = navController,
        )
    }
}

@Composable
internal fun rememberAppState(
    currentDestination: NavDestination?,
) = remember(currentDestination) {
    NewsAppState(currentDestination)
}
