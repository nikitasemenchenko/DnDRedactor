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
            name = DndRussianText.nameById(dto.index, dto.name),
            description = "",
            subraces = emptyList()
        )
    }

    fun classesResponseToDomain(dto: ApiResponseDto): CharacterClass {
        return CharacterClass(
            id = dto.index,
            name = DndRussianText.nameById(dto.index, dto.name),
            archetypes = emptyList()
        )
    }

    fun raceDtoToDomain(dto: RaceDto): Race {
        return Race(
            id = dto.index,
            name = DndRussianText.nameById(dto.index, dto.name),
            description = DndRussianText.raceDescription(dto.index),
            subraces = dto.subraces.map { subrace ->
                Subrace(
                    id = subrace.index,
                    name = DndRussianText.nameById(subrace.index, subrace.name),
                    description = ""
                )
            }
        )
    }

    fun subraceDtoToDomain(dto: SubraceDto): Subrace {
        return Subrace(
            id = dto.index,
            name = DndRussianText.nameById(dto.index, dto.name),
            description = DndRussianText.subraceDescription(dto.index)
        )
    }

    fun classDtoToDomain(dto: ClassDto): CharacterClass {
        return CharacterClass(
            id = dto.index,
            name = DndRussianText.nameById(dto.index, dto.name),
            archetypes = dto.subclasses.map { subClass ->
                Archetype(
                    id = subClass.index,
                    name = DndRussianText.nameById(subClass.index, subClass.name),
                    description = ""
                )
            }
        )
    }

    fun subclassDtoToDomain(dto: ArchetypeDto): Archetype {
        return Archetype(
            id = dto.index,
            name = DndRussianText.nameById(dto.index, dto.name),
            description = DndRussianText.archetypeDescription(dto.index)
        )
    }

    fun getRaceDescription(dto: RaceDto): String {
        return listOfNotNull(
            dto.alignment,
            dto.age,
            dto.sizeDescription
        ).joinToString(separator = "\n")
    }

    fun getSubclassDescription(dto: ArchetypeDto): String {
        return if(dto.desc.isNotEmpty()){
            dto.desc.joinToString("\n")
        } else ""
    }
}