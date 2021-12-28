package com.aherrera.tmdb.ui.screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aherrera.tmdb.R
import com.aherrera.tmdb.ui.backup.discover.DiscoverViewModel
import com.aherrera.tmdb.ui.components.MediaCardComponent
import com.aherrera.tmdb.ui.ui.theme.DarknesBlack
import com.aherrera.tmdb.ui.ui.theme.SuperWhite

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navigateToMediDetail: (Int, String) -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val discoverMovies by viewModel.movies.observeAsState()
    val discoverShows by viewModel.tvShows.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = DarknesBlack
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.last_movies),
                modifier = Modifier.padding(8.dp, 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SuperWhite
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 8.dp,
                    end = 16.dp,
                    bottom = 65.dp
                )
            ) {
                discoverMovies?.let { response ->
                    response.data?.let { safeList ->
                        items(safeList) { movieCard ->
                            MediaCardComponent(
                                mMovie = movieCard,
                                onMediaClick = {id, type ->
                                    navigateToMediDetail(id, type)
                                })
                        }
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.last_tv_shows),
                modifier = Modifier.padding(8.dp, 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SuperWhite
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 8.dp,
                    end = 16.dp,
                    bottom = 65.dp
                )
            ) {
                discoverShows?.let { response ->
                    response.data?.let { safeList ->
                        items(safeList) { showCard ->
                            MediaCardComponent(
                                tvShow = showCard,
                                onMediaClick = { id, type ->
                                    navigateToMediDetail(id, type)
                                })
                        }
                    }
                }
            }
        }
    }
}

