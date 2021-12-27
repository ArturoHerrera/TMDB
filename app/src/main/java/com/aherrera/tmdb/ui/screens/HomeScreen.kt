package com.aherrera.tmdb.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aherrera.tmdb.ui.backup.discover.DiscoverViewModel
import com.aherrera.tmdb.ui.components.MediaCardComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

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

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 65.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                discoverMovies?.let{ response ->
                    response.data?.let{ safeList ->
                        items(safeList) { movieCard ->
                            MediaCardComponent(movieCard)
                        }
                    }
                }


            }
        }
    }
}

