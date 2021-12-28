package com.aherrera.tmdb.ui.screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aherrera.tmdb.R
import com.aherrera.tmdb.ui.backup.detail.DetailsViewModel
import com.aherrera.tmdb.ui.components.MediaCardComponent
import com.aherrera.tmdb.utils.NetworkImage

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MediaDetailScreen(
    mediaId: Int,
    type: String,
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    viewModel.mediaId = mediaId
    viewModel.mediaType = type
    viewModel.requestMediaInfo()

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column {
            when (type) {
                "movie" -> {
                    val mMovie by viewModel.movie.observeAsState()
                    mMovie?.let { safeMovie ->
                        safeMovie.data?.let { safeData ->
                            Row {
                                Column(
                                    modifier = Modifier.size(140.dp, 210.dp)
                                ) {
                                    NetworkImage(
                                        modifier = Modifier
                                            .aspectRatio(0.8f)
                                            .size(140.dp, 210.dp)
                                            .padding(16.dp),
                                        url = safeData.getPosterUrl(),
                                    )
                                }
                                Column {
                                    Text(
                                        text = safeData.original_title,
                                        modifier = Modifier.padding(0.dp, 16.dp),
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = safeData.overview
                                    )
                                }
                            }
                        }
                    }
                }
                "tvShow" -> {
                    val mTvShow by viewModel.tvShow.observeAsState()

                    mTvShow?.let { safeTvShow ->
                        safeTvShow.data?.let { safeData ->
                            Row {
                                Column(
                                    modifier = Modifier.size(140.dp, 210.dp)
                                ) {
                                    NetworkImage(
                                        modifier = Modifier
                                            .aspectRatio(0.8f)
                                            .size(140.dp, 210.dp)
                                            .padding(16.dp),
                                        url = safeData.getPosterUrl(),
                                    )
                                }
                                Column {
                                    Text(
                                        text = safeData.original_name,
                                        modifier = Modifier.padding(0.dp, 16.dp),
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold
                                    )
                                    safeData.overview?.let {
                                        Text(
                                            text = it
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}

