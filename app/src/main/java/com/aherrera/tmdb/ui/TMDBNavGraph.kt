package com.aherrera.tmdb.ui

import android.os.Bundle
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aherrera.tmdb.ui.screens.HomeScreen
import com.aherrera.tmdb.ui.screens.MediaDetailScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

object Destinations {
    const val HOME_ROUTE = "home"
    const val HOME_ROUTE_PARAMS = "home/{mediaType}"
    const val MOVIE_ROUTE = "movie_detail"
    const val TV_SHOW_ROUTE = "tv_show_detail_detail"
    const val MEDIA_DETAIL = "media_detail"
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun TMDBNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.HOME_ROUTE) { backStackEntry ->
            HomeScreen(
                navigateToMediDetail = actions.navigateFromHomeToMediaDetail
            )
        }
        composable(Destinations.MEDIA_DETAIL) {
            val mediaId = navController.previousBackStackEntry?.arguments?.getInt("mediaId")
            val type = navController.previousBackStackEntry?.arguments?.getString("type")

            Log.wtf("testArgs", "graph --> id:$mediaId, type:$type")

            MediaDetailScreen(
                mediaId = mediaId!!,
                type = type!!,
                onBack = actions.upPress
            )
        }
    }
}


class MainActions(navController: NavHostController) {
    val navigateFromHomeToMediaDetail: (Int, String) -> Unit = { id, type ->
        navController.currentBackStackEntry?.arguments = bundleOf("mediaId" to id, "type" to type)
        navController.navigate(Destinations.MEDIA_DETAIL)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}