package com.example.movie_ui

data class Movie(
    val title: String,
    val rating: Float,
    val genres: String,
    val description: String,
    val imageUrl: String? = null // Optional field for image URL
)