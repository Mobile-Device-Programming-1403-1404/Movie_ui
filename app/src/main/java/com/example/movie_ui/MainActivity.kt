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
            composable("home") { HomeContent() }
            composable("profile") { ProfileScreen(navController = navController) }
            composable("discover") { DiscoverScreen(navController = navController) }
            composable(
                route = "movieDetails/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")
                // Replace with your actual movie data source
                MovieDetailsScreen(
                    movie = getSampleMovie(movieId ?: "1"),
                    navController = navController
                )
            }
        }
    }
}

fun getSampleMovie(id: String): Movie {
    return Movie(
        id = id,
        title = "Hitman's Wife's Bodyguard",
        rating = 3.5f,
        genres = "Action, Comedy, Crime",
        description = "The world's most lethal odd couple – bodyguard Michael Bryce and hitman Darius Kincaid are back on another life-threatening mission. The trio is forced to team up when Darius's wife, the infamous international con artist Sonia Kincaid, is kidnapped.",
        imageUrl = null,
        backdropUrl = null
    )
}