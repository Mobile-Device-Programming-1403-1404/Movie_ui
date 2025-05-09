package com.example.movie_ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

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