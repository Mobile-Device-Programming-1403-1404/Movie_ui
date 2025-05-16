package com.example.movie.model

data class Movie(
    val id: String,
    val title: String,
    val rating: Float,
    val genres: String,
    val description: String,
    val imageUrl: String
)
