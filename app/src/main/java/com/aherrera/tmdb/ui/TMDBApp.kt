package com.aherrera.tmdb.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.aherrera.tmdb.ui.ui.theme.TMDBTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun WarehouseApp() {
    TMDBTheme {
        val navController = rememberNavController()

        TMDBNavGraph(
            navController = navController
        )
    }
}