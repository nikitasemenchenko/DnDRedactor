package com.example.dndredactor.data.repository

import com.example.dndredactor.R
import com.example.dndredactor.data.CustomException
import com.example.dndredactor.data.model.LoginRequest
import com.example.dndredactor.data.model.RefreshRequest
import com.example.dndredactor.data.model.RegisterRequest
import com.example.dndredactor.data.remote.auth.AuthApi
import com.example.dndredactor.data.storage.TokenStorage
import java.io.IOException

class AuthRepository(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) {
    suspend fun login(login: String, password: String): Result<Unit> {
        return try {
            val response = api.login(LoginRequest(login, password))
            if (response.isSuccessful && response.body() != null) {
                val auth = response.body()!!
                val access = auth.accessToken
                val refresh = auth.refreshToken
                if (!access.isNullOrBlank() && !refresh.isNullOrBlank()) {
                    tokenStorage.saveAuthData(accessToken = access, refreshToken = refresh)
                    Result.success(Unit)
                } else {
                    Result.failure(CustomException(R.string.unableToLogin))
                }
            } else {
                Result.failure(CustomException(R.string.unableToLogin))
            }
        } catch (e: Exception) {
            Result.failure(CustomException(R.string.unableToLogin))
        }
    }

    suspend fun registration(
        username: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val response = api.register(RegisterRequest(username, email, password))
            if (response.isSuccessful && response.body() != null) {
                val auth = response.body()!!
                val access = auth.accessToken
                val refresh = auth.refreshToken
                if (!access.isNullOrBlank() && !refresh.isNullOrBlank()) {
                    tokenStorage.saveAuthData(accessToken = access, refreshToken = refresh)
                    Result.success(Unit)
                } else {
                    Result.failure(CustomException(R.string.unableToRegister))
                }
            } else {
                Result.failure(CustomException(R.string.unableToRegister))
            }
        } catch (e: Exception) {
            Result.failure(CustomException(R.string.unableToRegister, e.message, e.cause))
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            api.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        } finally {
            tokenStorage.clearAuthData()
        }
    }

    suspend fun refresh(): Result<String> {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrBlank()) {
            return Result.failure(CustomException(R.string.loadError))
        }

        return try {
            val response = api.refresh(RefreshRequest(refreshToken = refreshToken))

            if (response.isSuccessful) {
                val body =
                    response.body() ?: return Result.failure(CustomException(R.string.loadError))

                val newAccessToken = body.accessToken
                val newRefreshToken = body.refreshToken

                if (newAccessToken != null) {
                    if (newRefreshToken != null) {
                        tokenStorage.saveAuthData(newAccessToken, newRefreshToken)
                    } else {
                        tokenStorage.saveNewAccessToken(newAccessToken)
                    }
                    Result.success(newAccessToken)
                } else {
                    Result.failure(CustomException(R.string.loadError))
                }
            } else {
                tokenStorage.clearAuthData()
                Result.failure(CustomException(R.string.loadError))
            }

        } catch (e: IOException) {
            Result.failure(CustomException(R.string.internetError))
        } catch (e: Exception) {
            tokenStorage.clearAuthData()
            Result.failure(CustomException(R.string.loadError, e.message, e.cause))
        }
    }
}