package com.example.dndredactor.data.repository

import com.example.dndredactor.R
import com.example.dndredactor.data.CustomException
import com.example.dndredactor.data.model.CharacterPresentation
import com.example.dndredactor.data.model.presentation
import com.example.dndredactor.data.remote.CharacterApi

class CharacterRepository(
    private val api: CharacterApi,
) {
    suspend fun getCharacters(): Result<List<CharacterPresentation>> {
        return try {
            val response = api.getCharacters()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.presentation() })
            } else {
                Result.failure(CustomException(R.string.characterLoadError))
            }
        } catch (e: Exception) {
            Result.failure(CustomException(R.string.characterLoadError, e.message, e.cause))
        }
    }

    suspend fun deleteCharacter(id: String): Result<Unit> {
        return try {
            val response = api.deleteCharacter(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(CustomException(R.string.characterDeleteError))
            }
        } catch (e: Exception) {
            Result.failure(CustomException(R.string.characterDeleteError, e.message, e.cause))

        }
    }
}