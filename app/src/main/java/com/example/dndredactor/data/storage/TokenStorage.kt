package com.example.dndredactor.data.storage

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenStorage private constructor(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        STORAGE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAuthData(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
                .putString(REFRESH_TOKEN, refreshToken)
        }
    }

    fun clearAuthData() {
        sharedPreferences.edit { clear() }
    }

    fun saveNewAccessToken(newAccessToken: String?) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, newAccessToken)
        }
    }

    fun getAccessToken(): String? {
        val token = sharedPreferences.getString(ACCESS_TOKEN, null)
        return token
    }

    fun getRefreshToken(): String? {
        val token = sharedPreferences.getString(REFRESH_TOKEN, null)
        return token
    }

    companion object {
        private const val STORAGE_NAME = "TokenStorage"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"

        private var Instance: TokenStorage? = null

        fun getInstance(context: Context): TokenStorage {
            return Instance ?: synchronized(this) {
                Instance ?: TokenStorage(context.applicationContext).also { Instance = it }
            }
        }
    }
}