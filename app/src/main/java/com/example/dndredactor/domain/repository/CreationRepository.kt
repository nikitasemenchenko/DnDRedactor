package com.example.dndredactor.domain.repository

import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race

interface CreationRepository {
    suspend fun getRaces(): List<Race>
    suspend fun getClasses(): List<CharacterClass>

    suspend fun getRaceDetails(index: String): Race

    suspend fun getClassDetails(index: String): CharacterClass
}