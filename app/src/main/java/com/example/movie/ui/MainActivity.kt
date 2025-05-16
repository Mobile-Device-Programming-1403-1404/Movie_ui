package com.example.movie.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movie.api.movieAPI
import com.example.movie.model.Movie
import com.example.movie.navigation.BottomNavigationBar
import com.example.movie.navigation.Destinations
import com.example.movie.ui.screens.DiscoverScreen
import com.example.movie.ui.screens.HomeScreen
import com.example.movie.ui.screens.LoginScreen
import com.example.movie.ui.screens.MovieDetailsScreen
import com.example.movie.ui.screens.ProfileScreen
import com.example.movie.ui.screens.SignUpScreen
import com.example.movie.ui.theme.MovieTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val showBottomBar = currentRoute?.destination?.route in listOf(
        Destinations.HOME,
        Destinations.DISCOVER,
        Destinations.PROFILE
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destinations.LOGIN) {
                LoginScreen(navController)
            }
            composable(Destinations.SIGNUP) {
                SignUpScreen(navController)
            }
            composable(Destinations.HOME) {
                HomeScreen(navController)
            }
            composable(Destinations.PROFILE) {
                ProfileScreen()
            }
            composable(Destinations.DISCOVER) {
                DiscoverScreen(navController)
            }
            composable(
                Destinations.MOVIE_DETAILS,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("id") ?: "Unknown"
                var movie by remember { mutableStateOf<Movie?>(null) }
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(movieId) {
                    withContext(Dispatchers.IO) {
                        movie = movieAPI.getMovieById(movieId)
                        isLoading = false
                    }
                }

                when {
                    isLoading -> Text("Loading...", modifier = Modifier.fillMaxSize())
                    movie != null -> MovieDetailsScreen(movie = movie!!, navController = navController)
                    else -> Text("Movie not found", modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}