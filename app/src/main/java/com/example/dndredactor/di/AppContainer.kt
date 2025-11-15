package com.example.dndredactor.di

import android.content.Context
import com.example.dndredactor.data.AppConstants.BASE_URL
import com.example.dndredactor.data.AppConstants.TIMEOUT_SECONDS
import com.example.dndredactor.data.remote.CharacterApi
import com.example.dndredactor.data.remote.auth.AuthApi
import com.example.dndredactor.data.remote.auth.AuthInterceptor
import com.example.dndredactor.data.remote.auth.TokenAuthenticator
import com.example.dndredactor.data.repository.AuthRepository
import com.example.dndredactor.data.repository.CharacterRepository
import com.example.dndredactor.data.storage.TokenStorage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class AppContainer(private val context: Context) {
    val tokenStorage: TokenStorage by lazy {
        TokenStorage.getInstance(context)
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val basicOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    private val retrofitAuth: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(basicOkHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val authApi: AuthApi by lazy {
        retrofitAuth.create(AuthApi::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(authApi, tokenStorage)
    }

    val authInterceptor: AuthInterceptor by lazy {
        AuthInterceptor(tokenStorage)
    }

    private val tokenAuthenticator: TokenAuthenticator by lazy {
        TokenAuthenticator(authRepository, tokenStorage)
    }

    private val okHttpClientMain: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .callTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofitMain: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientMain)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val characterApi: CharacterApi by lazy {
        retrofitMain.create(CharacterApi::class.java)
    }

    val characterRepository: CharacterRepository by lazy {
        CharacterRepository(characterApi, tokenStorage)
    }
}
