package com.example.dndredactor.data.repository

import com.example.dndredactor.data.local.CharacterDao
import com.example.dndredactor.data.mappers.CharacterMapper
import com.example.dndredactor.data.model.CharacterPresentation
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

    override suspend fun deleteCharacter(id: Int) {
        characterDao.deleteCharacter(id)
    }
}