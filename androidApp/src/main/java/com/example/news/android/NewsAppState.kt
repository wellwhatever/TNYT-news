package com.example.news.android

import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import com.example.news.feature.article.destinations.ArticleListScreenDestination

internal class NewsAppState(
    navDestination: NavDestination?,
    val showSnackBar: Boolean = false
) {
    val showNavigationIcon = navDestination.isHomeScreen().not()

    @StringRes
    val navigationTitleRes: Int = if (navDestination.isHomeScreen()) {
        R.string.top_app_bar_home_title
    } else {
        R.string.top_app_bar_other_title
    }
}

fun NavDestination?.isHomeScreen() = this?.route == ArticleListScreenDestination.route