package com.aherrera.tmdb.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material.icons.outlined.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aherrera.tmdb.data.models.Movie
import com.aherrera.tmdb.data.models.TvShow
import com.aherrera.tmdb.ui.ui.theme.TMDBTheme
import com.aherrera.tmdb.utils.NetworkImage
import com.heka.compose_utils_kt.DateTimeUtils

@ExperimentalMaterialApi
@Composable
fun MediaCardComponent(mMovie: Movie, onMediaClick: (Int, String) -> Unit) {
    Card(
        backgroundColor = Color.Gray,
        modifier = Modifier.size(140.dp, 210.dp),
        elevation = 5.dp,
        onClick = { onMediaClick(mMovie.id, "movie") }
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            NetworkImage(
                modifier = Modifier.aspectRatio(0.8f),
                url = mMovie.getPosterUrl(),
            )
            Text(
                text = mMovie.original_title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MediaCardComponent(tvShow: TvShow, onMediaClick: (Int, String) -> Unit) {
    Card(
        backgroundColor = Color.Gray,
        modifier = Modifier.size(140.dp, 210.dp),
        elevation = 5.dp,
        onClick = { onMediaClick(tvShow.id, "tvShow") }
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            NetworkImage(
                modifier = Modifier.aspectRatio(0.8f),
                url = tvShow.getPosterUrl(),
            )
            Text(
                text = tvShow.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview("MediaType Card")
@Composable
fun PreviewMediaCardComponent() {
    TMDBTheme {
        MediaCardComponent(
            Movie(
                id = 1,
                adult = false,
                backdrop_path = "",
                genre_ids = listOf(),
                original_language = "EN",
                original_title = "Titulo original",
                overview = "Reseña Reseña Reseña Reseña Reseña Reseña Reseña abc etc.",
                popularity = 2.5F,
                poster_path = "https://image.tmdb.org/t/p/original/wigZBAmNrIhxp2FNGOROUAeHvdh.jpg",
                release_date = "24-Dic-2021",
                title = "El titulo",
                video = false,
                vote_average = 2.5F,
                vote_count = 7,
                page = 0,
            ), onMediaClick = { _id, _type ->

            }
        )
    }
}