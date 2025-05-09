package com.example.movie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movie.model.Movie

@Composable
fun MovieGridItem(movie: Movie, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate("movieDetails/${movie.id}")
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
            overflow = TextOverflow.Ellipsis,
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