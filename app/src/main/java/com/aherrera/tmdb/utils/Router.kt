package com.aherrera.tmdb.utils

object Router {
    const val base_api_url: String = "https://api.themoviedb.org/3/"
    const val base_image_url: String = "https://image.tmdb.org/t/p/w342"

    const val api_v3: String = "3/"
    const val api_v4: String = "4/"

    const val auth_path: String = "authentication/"

    object authentication {
        const val auth_token: String = "${auth_path}token/new"
    }
}