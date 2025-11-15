package com.example.dndredactor.data.remote.auth

import com.example.dndredactor.data.storage.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenStorage: TokenStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = tokenStorage.getAccessToken()
        val authRequest = if (!accessToken.isNullOrBlank()) {
            request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else {
            request
        }
        return chain.proceed(authRequest)
    }
}