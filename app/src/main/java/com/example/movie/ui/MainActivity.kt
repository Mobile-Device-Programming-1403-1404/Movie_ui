package com.example.movie.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movie.data.MockMovieApi
import com.example.movie.navigation.BottomNavigationBar
import com.example.movie.navigation.Destinations
import com.example.movie.ui.screens.DiscoverScreen
import com.example.movie.ui.screens.HomeScreen
import com.example.movie.ui.screens.MovieDetailsScreen
import com.example.movie.ui.screens.ProfileScreen
import com.example.movie.ui.theme.MovieTheme // Updated theme import

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTheme { // Updated theme call
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destinations.HOME) {
                HomeScreen(navController = navController)
            }
            composable(Destinations.PROFILE) {
                ProfileScreen() // No navController needed, as per updated signature
            }
            composable(Destinations.DISCOVER) {
                DiscoverScreen(navController = navController)
            }
            composable(
                Destinations.MOVIE_DETAILS,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("id") ?: "Unknown"
                val movie = MockMovieApi.topFiveMovies.find { it.id == movieId }
                    ?: MockMovieApi.latestMovies.find { it.id == movieId }
                    ?: MockMovieApi.discoverMovies.find { it.id == movieId }
                    ?: MockMovieApi.discoverMovies.first()
                MovieDetailsScreen(movie = movie, navController = navController)
            }
        }
    }
}