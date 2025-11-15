package com.example.dndredactor.data.repository

import com.example.dndredactor.data.AppConstants.characterDeleteError
import com.example.dndredactor.data.AppConstants.characterLoadError
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.remote.CharacterApi
import com.example.dndredactor.data.storage.TokenStorage

class CharacterRepository(
    private val api: CharacterApi,
    private val tokenStorage: TokenStorage
) {
    suspend fun getCharacters(): Result<List<Character>> {
        return try {
            val response = api.getCharacters()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(characterLoadError))
            }
        } catch (e: Exception) {
            Result.failure(Exception(characterLoadError))
        }
    }

    suspend fun deleteCharacter(id: String): Result<Unit> {
        return try {
            val response = api.deleteCharacter(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(characterDeleteError))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}