package com.example.movie.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movie.data.MockMovieApi
import com.example.movie.data.NetworkUtils
import com.example.movie.model.Movie
import com.example.movie.ui.components.LatestMovieItem
import com.example.movie.ui.components.MovieItem

@Composable
fun HomeScreen(navController: NavHostController) {
    // State for top five and latest movies
    var topFiveMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var latestMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Coroutine scope for launching API calls
    val scope = rememberCoroutineScope()

    // Fetch data when the composable is first loaded
    LaunchedEffect(Unit) {
        NetworkUtils.fetchData(
            scope = scope,
            onLoading = { isLoading = true },
            onComplete = { data ->
                topFiveMovies = data
                NetworkUtils.fetchData(
                    scope = scope,
                    onLoading = {},
                    onComplete = { latestData ->
                        latestMovies = latestData
                        isLoading = false
                    },
                    onError = { isLoading = false },
                    fetch = { MockMovieApi.getLatestMovies() }
                )
            },
            onError = { isLoading = false },
            fetch = { MockMovieApi.getTopFiveMovies() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Header section (fixed height)
            Text(
                text = "Top five.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (topFiveMovies.isEmpty()) {
                Text(
                    text = "No top five movies available.",
                    fontSize = 16.sp,
                    color = Color.Gray
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
                        items(topFiveMovies.size) { index ->
                            MovieItem(movie = topFiveMovies[index], navController = navController)
                        }
                    }
                }
            }

            // Middle section (fixed height)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Latest.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // List section (takes remaining space)
            Box(modifier = Modifier.weight(1f)) {
                if (latestMovies.isEmpty()) {
                    Text(
                        text = "No latest movies available.",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(latestMovies.size) { index ->
                            LatestMovieItem(movie = latestMovies[index], navController = navController)
                        }
                    }
                }
            }
        }
    }
}