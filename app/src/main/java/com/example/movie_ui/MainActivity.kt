package com.example.movie_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // For BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                ProfileScreen()
            }
            composable("discover") {
                DiscoverScreen()
            }
        }
    }
}

@Composable
fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Header section (fixed height)
        Text(
            text = "Top five.",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TopFiveSlider()

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
            LatestMovies()
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Profile Screen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DiscoverScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Discover Screen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TopFiveSlider() {
    val movies = listOf(
        Movie("Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime", "The world's most lethal odd couple...", "https://example.com/hitman.jpg"),
        Movie("Movie 2 with a Very Long Title That Should Be Truncated", 4.0f, "Action, Drama", "Description for Movie 2...", "https://example.com/movie2.jpg"),
        Movie("Movie 3", 3.8f, "Comedy, Thriller", "Description for Movie 3...", "https://example.com/movie3.jpg"),
        Movie("Movie 4", 4.2f, "Action, Sci-Fi", "Description for Movie 4...", "https://example.com/movie4.jpg"),
        Movie("Movie 5", 3.9f, "Drama, Romance", "Description for Movie 5...", "https://example.com/movie5.jpg")
    )

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
fun LatestMovies() {
    val latestMovies = listOf(
        Movie("Hitman's Wife's Bodyguard", 3.5f, "Action, Comedy, Crime",
            "The world's most lethal odd couple - bodyguard Michael Bryce and hitman Darius Kincaid - are back on another...",
            "https://example.com/hitman.jpg")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(latestMovies.size) { index ->
            LatestMovieItem(movie = latestMovies[index])
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
                .weight(0.4f)  // 35% of width
                .fillMaxHeight()  // Fill available height
                .background(Color.DarkGray, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Image Placeholder",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(0.6f)  // 65% of width
                .fillMaxHeight()  // Fill available height
        ) {
            Text(
                text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${movie.rating}",
                    fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                RatingStars(rating = movie.rating)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.genres,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.description,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))  // Takes remaining vertical space
        }
    }
}

@Composable
fun RatingStars(rating: Float) {
    Row {
        val fullStars = rating.toInt()
        val hasHalfStar = rating - fullStars >= 0.5
        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

        repeat(fullStars) {
            Text(
                text = "★",
                color = Color(0xFFFFD700),
                fontSize = 16.sp
            )
        }
        if (hasHalfStar) {
            Text(
                text = "★",
                color = Color(0xFFFFD700),
                fontSize = 16.sp
            )
        }
        repeat(emptyStars) {
            Text(
                text = "★",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            onClick = { navController.navigate("home") { popUpTo(navController.graph.startDestinationId) { inclusive = true } } }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            onClick = { navController.navigate("profile") { popUpTo(navController.graph.startDestinationId) { inclusive = true } } }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Discover") },
            label = { Text("Discover") },
            selected = currentRoute == "discover",
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            onClick = { navController.navigate("discover") { popUpTo(navController.graph.startDestinationId) { inclusive = true } } }
        )
    }
}