package com.example.dndredactor.data.repository

import com.example.dndredactor.data.local.CharacterDao
import com.example.dndredactor.data.local.CharacterEntity
import com.example.dndredactor.data.mappers.CharacterMapper
import com.example.dndredactor.data.model.CharacterPresentation
import com.example.dndredactor.data.model.Classes
import com.example.dndredactor.data.model.NewCharacter
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalCharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val mapper: CharacterMapper
): LocalCharacterRepository {
    override fun getCharacters(): Flow<List<CharacterPresentation>> {
        return characterDao.getCharacters()
            .map { characters ->
                characters.map {
                    mapper.CharacterEntityToPresentation(it)
                }
            }
    }

    override suspend fun createCharacter(character: NewCharacter) {
        characterDao.insertCharacter(
            CharacterEntity(
                name = character.fullName.trim(),
                level = 1,
                className = character.classId.toStoredClassName(),
                raceName = character.raceId.toString(),
                createdAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteCharacter(id: Int) {
        characterDao.deleteCharacter(id)
    }

    private fun String?.toStoredClassName(): String {
        return when (this) {
            "barbarian" -> Classes.BARBARIAN.name
            "bard" -> Classes.BARD.name
            "cleric" -> Classes.CLERIC.name
            "druid" -> Classes.DRUID.name
            "fighter" -> Classes.FIGHTER.name
            "monk" -> Classes.MONK.name
            "paladin" -> Classes.PALADIN.name
            "ranger" -> Classes.RANGER.name
            "rogue" -> Classes.ROGUE.name
            "sorcerer" -> Classes.SORCERER.name
            "warlock" -> Classes.WARLOCK.name
            "wizard" -> Classes.WIZARD.name
            else -> Classes.UNKNOWN.name
        }
    }
}