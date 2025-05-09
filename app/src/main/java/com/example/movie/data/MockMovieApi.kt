package com.example.movie.data

import com.example.movie.model.Movie
import com.example.movie.model.Profile
import kotlinx.coroutines.delay

object MockMovieApi {
    val topFiveMovies = listOf(
        Movie("1", "Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple...", "https://example.com/hitman.jpg"),
        Movie("2", "Movie 2 with a Very Long Title That Should Be Truncated", 4.0f, "Action, Drama", "Description for Movie 2...", "https://example.com/movie2.jpg"),
        Movie("3", "Movie 3", 3.8f, "Comedy, Thriller", "Description for Movie 3...", "https://example.com/movie3.jpg"),
        Movie("4", "Movie 4", 4.2f, "Action, Sci-Fi", "Description for Movie 4...", "https://example.com/movie4.jpg"),
        Movie("5", "Movie 5", 3.9f, "Drama, Romance", "Description for Movie 5...", "https://example.com/movie5.jpg")
    )

    val latestMovies = listOf(
        Movie("6", "Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple - bodyguard Michael Bryce and hitman Darius Kincaid - are back on another...", "https://example.com/hitman.jpg")
    )

    private val profile = Profile(
        username = "Artaz",
        averageRating = 4.3f,
        downloadedMovies = 37,
        email = "CallMeArtaz@gmail.com",
        birthDate = "2024/12/03",
        phoneNumber = "+98 913 111 111"
    )

    val discoverMovies = listOf(
        Movie("7", "Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple...", "https://example.com/hitman.jpg"),
        Movie("8", "The Incredibles", 4.1f, "Animation, Action, Adventure", "A family of superheroes...", "https://example.com/incredibles.jpg"),
        Movie("9", "Die Hard", 4.0f, "Action, Thriller", "A cop battles terrorists...", "https://example.com/diehard.jpg"),
        Movie("10", "Finding Nemo", 4.2f, "Animation, Family", "A clownfish searches for his son...", "https://example.com/nemo.jpg"),
        Movie("11", "Mission: Impossible", 3.9f, "Action, Thriller", "A spy on an impossible mission...", "https://example.com/missionimpossible.jpg"),
        Movie("12", "Toy Story", 4.3f, "Animation, Family", "Toys come to life...", "https://example.com/toystory.jpg")
    )

    suspend fun getTopFiveMovies(): List<Movie> {
        delay(1000)
        return topFiveMovies
    }

    suspend fun getLatestMovies(): List<Movie> {
        delay(1000)
        return latestMovies
    }

    suspend fun getProfile(): Profile {
        delay(1000)
        return profile
    }

    suspend fun getDiscoverMovies(category: String): List<Movie> {
        delay(1000)
        return when (category) {
            "ALL" -> discoverMovies
            "ANIMATION" -> discoverMovies.filter { it.genres.contains("Animation", ignoreCase = true) }
            "ACTION" -> discoverMovies.filter { it.genres.contains("Action", ignoreCase = true) }
            else -> emptyList()
        }
    }
}