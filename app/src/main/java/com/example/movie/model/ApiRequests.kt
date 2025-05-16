package com.example.movie.model

data class LoginRequest(val emailOrUsername: String, val password: String)
data class SignUpRequest(val email: String, val password: String, val username: String, val birthDate: String, val phoneNumber: String)
data class SuccessResponse(val success: Boolean)
data class ProfileRequest(val email: String)