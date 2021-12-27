package com.aherrera.tmdb.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aherrera.tmdb.R
import com.aherrera.tmdb.ui.backup.discover.DiscoverViewModel
import com.aherrera.tmdb.ui.components.MediaCardComponent

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navigateToMovie: () -> Unit,
    navigateToTvShow: () -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val discoverMovies by viewModel.movies.observeAsState()
    val discoverShows by viewModel.tvShows.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.last_movies)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 65.dp)
            ) {
                discoverMovies?.let{ response ->
                    response.data?.let{ safeList ->
                        items(safeList) { movieCard ->
                            MediaCardComponent(movieCard)
                        }
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.last_tv_shows)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 65.dp)
            ) {
                discoverShows?.let{ response ->
                    response.data?.let{ safeList ->
                        items(safeList) { showCard ->
                            MediaCardComponent(showCard)
                        }
                    }
                }
            }
        }
    }
}

