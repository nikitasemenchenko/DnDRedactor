package com.example.dndredactor.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.MasterKey

class TokenStorage private constructor(context: Context){
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "TokenStorage",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAuthData(accessToken: String, refreshToken: String){
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
                .putString(REFRESH_TOKEN, refreshToken)
        }
    }

    fun clearAuthData(){
        sharedPreferences.edit { clear() }
    }
    fun saveNewAccessToken(newAccessToken: String){
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, newAccessToken)
        }
    }

    fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN, null)
    fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN, null)

    companion object {
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