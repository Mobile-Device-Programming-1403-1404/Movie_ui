package com.example.movie_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun HomeContent() {
    // State for top five and latest movies
    var topFiveMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var latestMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Coroutine scope for launching API calls
    val scope = rememberCoroutineScope()

    // Fetch data when the composable is first loaded
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                // Fetch top five movies
                val topFive = MockMovieApi.getTopFiveMovies()
                topFiveMovies = topFive

                // Fetch latest movies
                val latest = MockMovieApi.getLatestMovies()
                latestMovies = latest
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (isLoading) {
            // Show loading indicator while fetching data
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
                TopFiveSlider(movies = topFiveMovies)
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
                    LatestMovies(movies = latestMovies)
                }
            }
        }
    }
}

@Composable
fun TopFiveSlider(movies: List<Movie>) {
    Box(
        modifier = Modifier
            .height(300.dp) // Static height for Top Five section
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies.size) { index ->
                MovieItem(movie = movies[index])
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.DarkGray, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Image Placeholder",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.Start)
                .width(180.dp) // Max width for title, leaving room for layout
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${movie.rating}",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            RatingStars(rating = movie.rating)
        }
    }
}

@Composable
fun LatestMovies(movies: List<Movie>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.size) { index ->
            LatestMovieItem(movie = movies[index])
        }
    }
}

@Composable
fun LatestMovieItem(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(0.4f)  // 40% of width
                .fillMaxHeight()  // Fill available height
                .background(Color.DarkGray, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Image Placeholder",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(0.6f)  // 60% of width
                .fillMaxHeight()  // Fill available height
        ) {
            Text(
                text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${movie.rating}",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                RatingStars(rating = movie.rating)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.genres,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.description,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)  // Takes remaining vertical space
            )
        }
    }
}