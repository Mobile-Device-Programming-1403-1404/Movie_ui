package com.example.movie.model

data class Profile(
    val email: String,
    val username: String,
    val birthDate: String,
    val phoneNumber: String,
    val averageRating: Float,
    val downloadedMovies: Int
)