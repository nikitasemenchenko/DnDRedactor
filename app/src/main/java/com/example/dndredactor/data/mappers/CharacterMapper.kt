package com.example.dndredactor.data.mappers

import com.example.dndredactor.data.local.CharacterEntity
import com.example.dndredactor.data.model.AbilityScores
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.model.ClassType
import com.example.dndredactor.data.model.Gender
import javax.inject.Inject

class CharacterMapper @Inject constructor() {
    fun entityToCharacter(entity: CharacterEntity): Character {
        return Character(
            id = entity.id,
            name = entity.name,
            level = entity.level,
            gender = entity.gender.toGender(),
            raceId = entity.raceId,
            subraceId = entity.subraceId,
            classType = entity.classType.toClassType(),
            classId = entity.classId,
            archetypeId = entity.archetypeId,
            personality = entity.personality,
            appearance = entity.appearance,
            ideal = entity.ideal,
            weakness = entity.weakness,
            attachment = entity.attachment,
            abilityScores = AbilityScores(
                strength = entity.strength,
                dexterity = entity.dexterity,
                constitution = entity.constitution,
                intelligence = entity.intelligence,
                wisdom = entity.wisdom,
                charisma = entity.charisma
            )
        )
    }

    private fun String.toClassType(): ClassType {
        return runCatching {
            ClassType.valueOf(this)
        }.getOrElse {
            ClassType.UNKNOWN
        }
    }

    private fun String.toGender(): Gender {
        return runCatching {
            Gender.valueOf(this)
        }.getOrElse {
            Gender.UNSPECIFIED
        }
    }

}