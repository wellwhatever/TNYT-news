package com.example.news.android.navigation

import com.example.news.feature.article.ArticleNavGraph
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object AppNavGraph : NavGraphSpec {
    override val route = "appGraph"
    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
    override val startRoute = ArticleNavGraph
    override val nestedNavGraphs: List<NavGraphSpec> = listOf(
        ArticleNavGraph
    )
}