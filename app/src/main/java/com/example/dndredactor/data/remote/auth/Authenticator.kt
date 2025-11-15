package com.example.dndredactor.data.remote.auth

import com.example.dndredactor.data.repository.AuthRepository
import com.example.dndredactor.data.storage.TokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Refresh-Attempted") == "true") return null

        synchronized(lock) {
            // проверить, возможно другой поток уже обновил токен
            val currentAccess = tokenStorage.getAccessToken()
            val requestAccess = extractBearer(response.request.header("Authorization"))
            if (!requestAccess.isNullOrBlank() && requestAccess != currentAccess) {
                val token = tokenStorage.getAccessToken() ?: return null
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .header("Refresh-Attempted", "true")
                    .build()
            }

            val result = runBlocking(Dispatchers.IO) {
                authRepository.refresh()
            }

            val newToken = result.getOrNull() ?: return null

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .header("Refresh-Attempted", "true")
                .build()
        }
    }

    private fun extractBearer(header: String?): String? {
        if (header == null) return null
        val parts = header.split(" ")
        return if (parts.size == 2 && parts[0].equals("Bearer", true)) parts[1] else null
    }

}
