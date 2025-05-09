package com.example.movie_ui.mocks

import com.example.movie_ui.Movie

object MockApi {
    fun getTopFiveMovies(): List<Movie> {
        return listOf(
            Movie("Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple - bodyguard Michael Bryce and hitman Darius Kincaid - are back on another..."),
            Movie("Movie 2", 4.0f, "Action, Drama", "Description for Movie 2..."),
            Movie("Movie 3", 3.8f, "Comedy, Thriller", "Description for Movie 3..."),
            Movie("Movie 4", 4.2f, "Action, Sci-Fi", "Description for Movie 4..."),
            Movie("Movie 5", 3.9f, "Drama, Romance", "Description for Movie 5...")
        )
    }

    fun getLatestMovies(): List<Movie> {
        return listOf(
            Movie("Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple - bodyguard Michael Bryce and hitman Darius Kincaid - are back on another..."),
            Movie("Latest Movie 2", 4.1f, "Horror, Thriller", "Description for Latest Movie 2...")
        )
    }
}