package com.example.dndredactor.data.repository

import com.example.dndredactor.data.model.LoginRequest
import com.example.dndredactor.data.model.RefreshRequest
import com.example.dndredactor.data.model.RegisterRequest
import com.example.dndredactor.data.remote.auth.AuthApi
import com.example.dndredactor.data.storage.TokenStorage

class AuthRepository(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) {
    suspend fun login(login: String, password: String): Result<Unit> {
        return try {
            val response = api.login(LoginRequest(login, password))
            if (response.isSuccessful && response.body() != null) {
                val auth = response.body()!!
                tokenStorage.saveAuthData(
                    accessToken = auth.access_token,
                    refreshToken = auth.refresh_token
                )
                Result.success(Unit)
            } else {
                Result.failure(Exception("${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registration(
        username: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val response = api.register(
                RegisterRequest(
                    username, email, password
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val auth = response.body()!!
                tokenStorage.saveAuthData(
                    accessToken = auth.access_token,
                    refreshToken = auth.refresh_token
                )
                Result.success(Unit)
            } else {
                Result.failure(Exception("${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val response = api.logout()
            if (response.isSuccessful) {
                tokenStorage.clearAuthData()
                Result.success(Unit)
            } else {
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(Exception())
        }
    }

    suspend fun refresh(): Result<String> {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrBlank()) {
            return Result.failure(Exception())
        }

        return try {
            val response = api.refresh(RefreshRequest(refresh_token = refreshToken))
            if (response.isSuccessful && response.body() != null) {
                val newAccessToken = response.body()!!.access_token
                tokenStorage.saveNewAccessToken(newAccessToken)
                Result.success(newAccessToken)
            } else {
                tokenStorage.clearAuthData()
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            tokenStorage.clearAuthData()
            Result.failure(Exception())
        }
    }
}