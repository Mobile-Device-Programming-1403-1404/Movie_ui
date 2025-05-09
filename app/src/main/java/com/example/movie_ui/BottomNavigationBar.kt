package com.example.movie_ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

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