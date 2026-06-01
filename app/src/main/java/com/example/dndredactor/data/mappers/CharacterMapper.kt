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
            raceName = entity.raceName,
            subraceId = entity.subraceId,
            subraceName = entity.subraceName,
            classType = entity.classType.toClassType(),
            classId = entity.classId,
            className = entity.className,
            archetypeId = entity.archetypeId,
            archetypeName = entity.archetypeName,
            backstory = entity.backstory,
            equipment = entity.equipment,
            armorClass = entity.armorClass,
            maxHitPoints = entity.maxHitPoints,
            currentHitPoints = entity.currentHitPoints,
            additionalInfo = entity.additionalInfo,
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
            ),
            createdAt = entity.createdAt
        )
    }

    fun characterToEntity(character: Character): CharacterEntity{
        return CharacterEntity(
            id = character.id,
            name = character.name.trim(),
            gender = character.gender.name,
            level = character.level,

            raceId = character.raceId,
            raceName = character.raceName,

            subraceId = character.subraceId,
            subraceName = character.subraceName,

            classType = character.classType.name,
            classId = character.classId,
            className = character.className,

            archetypeId = character.archetypeId,
            archetypeName = character.archetypeName,

            backstory = character.backstory.trim(),
            equipment = character.equipment.trim(),

            armorClass = character.armorClass,
            maxHitPoints = character.maxHitPoints,
            currentHitPoints = character.currentHitPoints,

            additionalInfo = character.additionalInfo.trim(),

            appearance = character.appearance.trim(),
            personality = character.personality.trim(),
            ideal = character.ideal.trim(),
            attachment = character.attachment.trim(),
            weakness = character.weakness.trim(),

            strength = character.abilityScores.strength,
            dexterity = character.abilityScores.dexterity,
            constitution = character.abilityScores.constitution,
            intelligence = character.abilityScores.intelligence,
            wisdom = character.abilityScores.wisdom,
            charisma = character.abilityScores.charisma,

            createdAt = character.createdAt
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