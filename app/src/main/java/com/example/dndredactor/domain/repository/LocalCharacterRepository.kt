package com.example.dndredactor.domain.repository

import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.model.CharacterDraft
import kotlinx.coroutines.flow.Flow

interface LocalCharacterRepository {
    fun getCharacters(): Flow<List<Character>>

    fun getCharacter(id: Int): Flow<Character?>

    suspend fun createCharacter(character: CharacterDraft)

    suspend fun deleteCharacter(id: Int)
}