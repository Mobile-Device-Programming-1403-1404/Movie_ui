package com.example.movie_ui

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val rating: Float,
    val genres: String,
    val description: String,
    val imageUrl: String
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}

data class Profile(
    val username: String,
    val averageRating: Float,
    val downloadedMovies: Int,
    val email: String,
    val birthDate: String,
    val phoneNumber: String
)