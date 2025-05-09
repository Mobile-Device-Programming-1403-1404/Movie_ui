package com.example.movie_ui.mocks

import com.example.movie_ui.Movie

object MockApi {
    fun getTopFiveMovies(): List<Movie> {
        return listOf(
            Movie("1","Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple...", "https://example.com/hitman.jpg"),
            Movie("2","Movie 2 with a Very Long Title That Should Be Truncated", 4.0f, "Action, Drama", "Description for Movie 2...", "https://example.com/movie2.jpg"),
            Movie("3","Movie 3", 3.8f, "Comedy, Thriller", "Description for Movie 3...", "https://example.com/movie3.jpg"),
            Movie("4","Movie 4", 4.2f, "Action, Sci-Fi", "Description for Movie 4...", "https://example.com/movie4.jpg"),
            Movie("5","Movie 5", 3.9f, "Drama, Romance", "Description for Movie 5...", "https://example.com/movie5.jpg")
        )
    }

    fun getLatestMovies(): List<Movie> {
        return listOf(
            Movie("1","Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime",
                "The world's most lethal odd couple - bodyguard Michael Bryce and hitman Darius Kincaid - are back on another...",
                "https://example.com/hitman.jpg")
        )
    }
}