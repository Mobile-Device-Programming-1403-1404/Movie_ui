package com.example.movie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun LatestMovieItem(movie: Movie, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("movieDetails/${movie.id}") }
    ) {
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
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
                .weight(0.6f)
                .fillMaxHeight()
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
                    .weight(1f)
            )
        }
    }
}