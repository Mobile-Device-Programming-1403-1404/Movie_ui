package com.example.movie.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movie.api.MockMovieApi
import com.example.movie.model.Movie
import com.example.movie.ui.components.LatestMovieItem
import com.example.movie.ui.components.MovieGridItem
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var topFiveMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var latestMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                // Fetch both lists concurrently
                val topFiveDeferred = scope.async { MockMovieApi.getTopFiveMovies() }
                val latestDeferred = scope.async { MockMovieApi.getLatestMovies() }
                topFiveMovies = topFiveDeferred.await()
                latestMovies = latestDeferred.await()
                isLoading = false
                if (topFiveMovies.isEmpty() && latestMovies.isEmpty()) {
                    errorMessage = "No movies available"
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Failed to load movies: ${e.message}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Home",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else if (errorMessage.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    Text(
                        text = "Top Five",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (topFiveMovies.isEmpty()) {
                        Text(
                            text = "No top five movies available",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(topFiveMovies) { movie ->
                                    MovieGridItem(movie = movie, navController = navController)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Latest",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        if (latestMovies.isEmpty()) {
                            Text(
                                text = "No latest movies available",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(latestMovies) { movie ->
                                    LatestMovieItem(movie = movie, navController = navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}