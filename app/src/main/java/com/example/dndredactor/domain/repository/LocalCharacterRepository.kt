package com.example.dndredactor.domain.repository

import com.example.dndredactor.data.model.CharacterPresentation
import com.example.dndredactor.data.model.NewCharacter
import kotlinx.coroutines.flow.Flow

interface LocalCharacterRepository {
    fun getCharacters(): Flow<List<CharacterPresentation>>

    suspend fun createCharacter(character: NewCharacter)

    suspend fun deleteCharacter(id: Int)
}