package com.example.movie.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingStars(rating: Float, starSize: TextUnit = 16.sp) {
    Row {
        val fullStars = rating.toInt()
        val hasHalfStar = rating - fullStars >= 0.5
        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

        repeat(fullStars) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(starSize.value.dp)
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(starSize.value.dp)
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(starSize.value.dp)
            )
        }
    }
}