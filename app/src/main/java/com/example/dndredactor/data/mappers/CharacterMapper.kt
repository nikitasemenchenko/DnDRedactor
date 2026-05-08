package com.example.dndredactor.data.mappers

import com.example.dndredactor.data.local.CharacterEntity
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.model.ClassType
import javax.inject.Inject

class CharacterMapper @Inject constructor() {
    fun entityToCharacter(entity: CharacterEntity): Character {
        return Character(
            id = entity.id,
            name = entity.name,
            level = entity.level,
            classType = entity.classType.toClassType(),
            raceId = entity.raceId
        )
    }

    private fun String?.toClassType(): ClassType {
        if(this == null) return ClassType.UNKNOWN
        return runCatching {
            ClassType.valueOf(this)
        }.getOrElse {
            ClassType.UNKNOWN
        }
    }

}