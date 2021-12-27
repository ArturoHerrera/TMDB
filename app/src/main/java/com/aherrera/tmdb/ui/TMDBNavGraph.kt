package com.aherrera.tmdb.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aherrera.tmdb.ui.screens.HomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

object Destinations {
    const val HOME_ROUTE = "home"
    const val HOME_ROUTE_PARAMS = "home/{mediaType}"
    const val MOVIE_ROUTE = "movie_detail"
    const val TV_SHOW_ROUTE = "tv_show_detail_detail"
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
                navigateToMovie = actions.navigateFromHomeToMovieDetail,
                navigateToTvShow = actions.navigateFromHomeToTvShowDetail
            )
        }
    }
}


class MainActions(navController: NavHostController) {
    val navigateFromHomeToMovieDetail: () -> Unit = {
        navController.navigate("${Destinations.MOVIE_ROUTE}/movie") {
            popUpTo(Destinations.HOME_ROUTE) { inclusive = true }
        }
    }
    val navigateFromHomeToTvShowDetail: () -> Unit = {
        navController.navigate("${Destinations.MOVIE_ROUTE}/tvShow") {
            popUpTo(Destinations.HOME_ROUTE) { inclusive = true }
        }
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}