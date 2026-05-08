package com.example.dndredactor.data.repository

import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.local.CharacterDao
import com.example.dndredactor.data.local.CharacterEntity
import com.example.dndredactor.data.mappers.CharacterMapper
import com.example.dndredactor.data.model.CharacterDraft
import com.example.dndredactor.data.model.ClassType
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalCharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val mapper: CharacterMapper
): LocalCharacterRepository {

    override fun getCharacters(): Flow<List<Character>> {
        return characterDao.getCharacters()
            .map { characters ->
                characters.map {
                    mapper.entityToCharacter(it)
                }
            }
    }

    override suspend fun createCharacter(character: CharacterDraft) {
        characterDao.insertCharacter(
            CharacterEntity(
                name = character.fullName.trim(),
                level = 1,
                classType = character.classId.toClassName(),
                raceId = character.raceId.toString(),
                createdAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteCharacter(id: Int) {
        characterDao.deleteCharacter(id)
    }

    private fun String?.toClassName(): String {
        return when (this) {
            "barbarian" -> ClassType.BARBARIAN.name
            "bard" -> ClassType.BARD.name
            "cleric" -> ClassType.CLERIC.name
            "druid" -> ClassType.DRUID.name
            "fighter" -> ClassType.FIGHTER.name
            "monk" -> ClassType.MONK.name
            "paladin" -> ClassType.PALADIN.name
            "ranger" -> ClassType.RANGER.name
            "rogue" -> ClassType.ROGUE.name
            "sorcerer" -> ClassType.SORCERER.name
            "warlock" -> ClassType.WARLOCK.name
            "wizard" -> ClassType.WIZARD.name
            else -> ClassType.UNKNOWN.name
        }
    }
}