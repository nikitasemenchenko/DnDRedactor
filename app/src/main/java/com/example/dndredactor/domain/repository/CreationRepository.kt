package com.example.dndredactor.domain.repository

import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race

interface CreationRepository {
    fun getRaces(): List<Race>
    fun getClasses(): List<CharacterClass>
}