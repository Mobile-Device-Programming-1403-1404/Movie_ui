package com.example.movie.model

data class Profile(
    val username: String,
    val averageRating: Float,
    val downloadedMovies: Int,
    val email: String,
    val birthDate: String,
    val phoneNumber: String
)