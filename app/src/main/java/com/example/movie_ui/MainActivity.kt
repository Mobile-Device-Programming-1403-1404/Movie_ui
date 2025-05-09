package com.example.movie_ui

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
import com.example.movie_ui.ui.theme.Movie_uiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movie_uiTheme {
                val navController = rememberNavController()
                HomeScreen(navController = navController)
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeContent()
            }
            composable("profile") {
                ProfileScreen(navController = navController)
            }
            composable("discover") {
                DiscoverScreen(navController = navController)
            }
            composable(
                "movieDetails/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("id") ?: "Unknown"
                // Note: In a real app, you'd need to pass the movie list from DiscoverScreen to MainActivity
                // For simplicity, we'll assume the movie list is available in a ViewModel or passed via navigation
                MovieDetailsScreen(
                    movie = MockMovieApi.discoverMovies.find { it.id == movieId } ?: MockMovieApi.discoverMovies.first(),
                    navController = navController
                )
            }
        }
    }
}