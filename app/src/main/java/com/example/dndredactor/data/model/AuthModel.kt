package com.example.dndredactor.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val access_token: String,
    val refresh_token: String,
    val token_type: String
)

@Serializable
data class RefreshRequest(
    val refresh_token: String
)

@Serializable
data class RefreshResponse(
    val access_token: String,
    val token_type: String
)