package com.example.dndredactor.domain.repository

import com.example.dndredactor.data.model.CharacterPresentation
import kotlinx.coroutines.flow.Flow

interface LocalCharacterRepository {
    fun getCharacters(): Flow<List<CharacterPresentation>>

    suspend fun deleteCharacter(id: Int)
}