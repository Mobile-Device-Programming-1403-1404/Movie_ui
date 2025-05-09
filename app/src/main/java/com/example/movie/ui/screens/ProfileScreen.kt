package com.example.movie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movie.data.MockMovieApi
import com.example.movie.data.NetworkUtils
import com.example.movie.model.Profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() { // Added navController parameter
    // State for profile data and loading
    var profile by remember { mutableStateOf<Profile?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Coroutine scope for launching API calls
    val scope = rememberCoroutineScope()

    // Fetch profile data when the composable is first loaded
    LaunchedEffect(Unit) {
        NetworkUtils.fetchData(
            scope = scope,
            onLoading = { isLoading = true },
            onComplete = { data ->
                profile = data.firstOrNull()
                isLoading = false
            },
            onError = { isLoading = false },
            fetch = { listOf(MockMovieApi.getProfile()) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile.", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
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
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (profile != null) {
                    // Profile Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Avatar",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .padding(8.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = profile!!.username,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Stats Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${profile!!.averageRating}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Average Rating",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${profile!!.downloadedMovies}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Downloaded Movies",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Details Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Email address",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = profile!!.email,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Birth of date",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = profile!!.birthDate,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Phone Number",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = profile!!.phoneNumber,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Failed to load profile.",
                        fontSize = 16.sp,
                        color = Color.Red,
                    )
                }
            }
        }
    )
}