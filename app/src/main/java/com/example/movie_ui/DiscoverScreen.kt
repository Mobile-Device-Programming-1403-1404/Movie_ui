package com.example.movie_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(navController: NavHostController) {
    // State for movies, loading, selected tab, and search query
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var allMovies by remember { mutableStateOf<List<Movie>>(emptyList()) } // Store unfiltered movies
    var isLoading by remember { mutableStateOf(true) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    // Define tabs and their corresponding categories
    val tabs = listOf("ALL", "ANIMATION", "ACTION")
    val categories = listOf("ALL", "ANIMATION", "ACTION")

    // Coroutine scope for launching API calls
    val scope = rememberCoroutineScope()

    // Fetch all movies when the composable is first loaded
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                isLoading = true
                val allMoviesData = MockMovieApi.getDiscoverMovies("ALL")
                allMovies = allMoviesData
                movies = allMoviesData // Initial display of all movies
            } finally {
                isLoading = false
            }
        }
    }

    // Update movies based on selected tab
    LaunchedEffect(selectedTabIndex) {
        scope.launch {
            val category = categories[selectedTabIndex]
            movies = if (searchQuery.isEmpty()) {
                MockMovieApi.getDiscoverMovies(category)
            } else {
                allMovies.filter { it.title.contains(searchQuery, ignoreCase = true) || it.genres.contains(searchQuery, ignoreCase = true) }
                    .filter { category == "ALL" || it.genres.contains(category, ignoreCase = true) }
            }
        }
    }

    // Update movies based on search query
    LaunchedEffect(searchQuery) {
        scope.launch {
            val category = categories[selectedTabIndex]
            movies = if (searchQuery.isEmpty()) {
                MockMovieApi.getDiscoverMovies(category)
            } else {
                allMovies.filter { it.title.contains(searchQuery, ignoreCase = true) || it.genres.contains(searchQuery, ignoreCase = true) }
                    .filter { category == "ALL" || it.genres.contains(category, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discover.", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    placeholder = { Text("Search...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                )

                // Tab Row with dynamic counts
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, _ ->
                        val count = when (categories[index]) {
                            "ALL" -> movies.size
                            "ANIMATION" -> movies.count { it.genres.contains("Animation", ignoreCase = true) }
                            "ACTION" -> movies.count { it.genres.contains("Action", ignoreCase = true) }
                            else -> 0
                        }
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text("${categories[index]} ($count)") }
                        )
                    }
                }

                // Movie Grid or Loading State
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (movies.isEmpty()) {
                    Text(
                        text = "No movies available in this category.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(movies.size) { index ->
                            MovieGridItem(
                                movie = movies[index],
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieGridItem(movie: Movie, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate("movieDetails/${movie.id}") // Pass movie ID
            }
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.DarkGray, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            modifier = Modifier.width(130.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${movie.rating}",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            RatingStars(rating = movie.rating)
        }
    }
}