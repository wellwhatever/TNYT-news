package com.example.news.android.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object AppNavGraph : NavGraphSpec {
    override val destinationsByRoute: Map<String, DestinationSpec<*>>
        get() = TODO("Not yet implemented")
    override val route: String
        get() = TODO("Not yet implemented")
    override val startRoute: Route
        get() = TODO("Not yet implemented")

    override val nestedNavGraphs: List<NavGraphSpec>
        get() = super.nestedNavGraphs

}