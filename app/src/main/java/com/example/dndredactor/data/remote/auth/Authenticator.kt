package com.example.dndredactor.data.remote.auth

import com.example.dndredactor.data.repository.AuthRepository
import com.example.dndredactor.data.storage.TokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenStorage: TokenStorage,
    private val authRepository: AuthRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.request.header("Refresh-Attempted") != null) {
            return null
        }

        val newToken = runBlocking {
            val result = authRepository.refresh()

            if (result.isFailure) {
                tokenStorage.clearAuthData()
            }

            result.getOrNull()
        }

        if (newToken.isNullOrBlank()) {
            return null
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .header("Refresh-Attempted", "true")
            .build()
    }
}