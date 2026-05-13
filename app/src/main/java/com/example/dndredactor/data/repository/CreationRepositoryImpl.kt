package com.example.dndredactor.data.repository

import com.example.dndredactor.data.mappers.DtoMapper
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.remote.DndApi
import com.example.dndredactor.domain.repository.CreationRepository
import javax.inject.Inject

class CreationRepositoryImpl @Inject constructor(
    private val dndApi: DndApi,
    private val mapper: DtoMapper
) : CreationRepository {

    private var cachedRaces: List<Race>? = null
    private var cachedClasses: List<CharacterClass>? = null

    private val raceDetailsCache = mutableMapOf<String, Race>()
    private val classDetailsCache = mutableMapOf<String, CharacterClass>()

    override suspend fun getRaces(): List<Race> {
        cachedRaces?.let { return it }

        val races = dndApi.getRaces()
            .results
            .map { raceDto ->
                mapper.racesResponseToDomain(raceDto)
            }
        cachedRaces = races
        return races
    }

    override suspend fun getClasses(): List<CharacterClass>  {
        cachedClasses?.let { return it }

        val classes = dndApi.getClasses()
            .results
            .map { classDto ->
                mapper.classesResponseToDomain(classDto)
            }

        cachedClasses = classes
        return classes
    }

    override suspend fun getRaceDetails(index: String): Race {
        raceDetailsCache[index]?.let { return it }

        val race = mapper.raceDtoToDomain(
            dndApi.getRace(index)
        )
        raceDetailsCache[index] = race
        return race
    }

    override suspend fun getClassDetails(index: String): CharacterClass {
        classDetailsCache[index]?.let { return it }

        val characterClass = mapper.classDtoToDomain(
            dndApi.getClass(index)
        )
        classDetailsCache[index] = characterClass
        return characterClass
    }
}