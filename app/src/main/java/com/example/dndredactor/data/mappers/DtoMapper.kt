package com.example.dndredactor.data.mappers

import com.example.dndredactor.data.model.Archetype
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.data.remote.dto.ApiResponseDto
import com.example.dndredactor.data.remote.dto.ArchetypeDto
import com.example.dndredactor.data.remote.dto.ClassDto
import com.example.dndredactor.data.remote.dto.RaceDto
import com.example.dndredactor.data.remote.dto.SubraceDto
import javax.inject.Inject

class DtoMapper @Inject constructor(){

    fun racesResponseToDomain(dto: ApiResponseDto): Race {
        return Race(
            id = dto.index,
            name = dto.name,
            description = "",
            subraces = emptyList()
        )
    }

    fun classesResponseToDomain(dto: ApiResponseDto): CharacterClass {
        return CharacterClass(
            id = dto.index,
            name = dto.name,
            archetypes = emptyList()
        )
    }

    fun raceDtoToDomain(dto: RaceDto): Race {
        return Race(
            id = dto.index,
            name = dto.name,
            description = getRaceDescription(dto),
            subraces = dto.subraces.map { subrace ->
                Subrace(
                    id = subrace.index,
                    name = subrace.name,
                    description = ""
                )
            }
        )
    }

    fun classDtoToDomain(dto: ClassDto): CharacterClass {
        return CharacterClass(
            id = dto.index,
            name = dto.name,
            archetypes = dto.subclasses.map { subClass ->
                Archetype(
                    id = subClass.index,
                    name = subClass.name,
                    description = ""
                )
            }
        )

    }

    fun getRaceDescription(dto: RaceDto): String {
        return listOfNotNull(
            dto.alignment,
            dto.age,
            dto.sizeDescription
        ).joinToString(separator = "\n")
    }

    fun subclassDtoToDomain(dto: ArchetypeDto): Archetype {
        return Archetype(
            id = dto.index,
            name = dto.name,
            description = getSubclassDescription(dto)
        )
    }

    fun getSubclassDescription(dto: ArchetypeDto): String {
        return if(dto.desc.isNotEmpty()){
            dto.desc.joinToString(" ")
        } else ""
    }

    fun subraceDtoToDomain(dto: SubraceDto): Subrace {
        return Subrace(
            id = dto.index,
            name = dto.name,
            description = dto.desc ?: ""
        )
    }
}