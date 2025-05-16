package com.example.movie.api

import com.example.movie.model.LoginRequest
import com.example.movie.model.Movie
import com.example.movie.model.Profile
import com.example.movie.model.ProfileRequest
import com.example.movie.model.SignUpRequest
import com.example.movie.model.SuccessResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("api/movies/top5")
    suspend fun getTopFiveMovies(): List<Movie>

    @GET("api/movies/latest")
    suspend fun getLatestMovies(): List<Movie>

    @GET("api/movies/search")
    suspend fun getDiscoverMovies(
        @Query("category") category: String,
        @Query("query") query: String? = null
    ): List<Movie>

    @GET("api/movies/{id}")
    suspend fun getMovieById(@Path("id") id: String): Movie

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): SuccessResponse

    @POST("api/signup")
    suspend fun signUp(@Body request: SignUpRequest): SuccessResponse

    @POST("api/user/profile")
    suspend fun getProfile(@Body request: ProfileRequest): Profile
}