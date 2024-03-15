package com.example.news.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.news.android.navigation.AppNavGraph
import com.example.news.android.theme.TNYTTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.spec.NavGraphSpec

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TNYTTheme {
                TNYTApplication()
            }
        }
    }
}

@Composable
fun TNYTApplication(
    modifier: Modifier = Modifier,
) {
    DestinationsNavHost(
        modifier = modifier,
        navGraph = AppNavGraph
    )
}
