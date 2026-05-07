package com.example.dndredactor.data.mappers

import com.example.dndredactor.data.local.CharacterEntity
import com.example.dndredactor.data.model.CharacterPresentation
import com.example.dndredactor.data.model.Classes
import javax.inject.Inject

class CharacterMapper @Inject constructor() {
    fun CharacterEntityToPresentation(entity: CharacterEntity): CharacterPresentation {
        return CharacterPresentation(
            id = entity.id,
            name = entity.name,
            level = entity.level,
            characterClass = entity.className.toCharacterClassFromStorage()
        )
    }

    private fun String.toCharacterClassFromStorage(): Classes {
        return runCatching {
            Classes.valueOf(this)
        }.getOrElse {
            Classes.UNKNOWN
        }
    }

}