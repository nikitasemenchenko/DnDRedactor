package com.example.dndredactor.data.remote.auth

import com.example.dndredactor.data.model.AuthResponse
import com.example.dndredactor.data.model.LoginRequest
import com.example.dndredactor.data.model.MeInfo
import com.example.dndredactor.data.model.RefreshRequest
import com.example.dndredactor.data.model.RefreshResponse
import com.example.dndredactor.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/refresh")
    suspend fun refresh(
        @Body request: RefreshRequest
    ): Response<RefreshResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("auth/me")
    suspend fun getMe(): Response<MeInfo>
}