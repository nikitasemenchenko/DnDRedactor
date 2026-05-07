package com.example.dndredactor.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val level: Int,
    @SerialName("class") val className: String
)

@Serializable
data class CharacterDetails(
    val id: String,
    val name: String,
    val level: Int,
    @SerialName("class") val className: String,
    val stats: Stats,
    val skills: Skills,
    val savingThrows: SavingThrows
)

@Serializable
data class Stats(
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
)


@Serializable
data class Skills(
    val athletics: Int? = null,
    val acrobatics: Int? = null,
    val sleightOfHand: Int? = null,
    val stealth: Int? = null,
    val arcana: Int? = null,
    val history: Int? = null,
    val investigation: Int? = null,
    val nature: Int? = null,
    val religion: Int? = null,
    val animalHandling: Int? = null,
    val insight: Int? = null,
    val medicine: Int? = null,
    val perception: Int? = null,
    val survival: Int? = null,
    val deception: Int? = null,
    val intimidation: Int? = null,
    val performance: Int? = null,
    val persuasion: Int? = null,
)

@Serializable
data class CreateCharacterRequest(
    val name: String,
    @SerialName("class") val className: String,
    val race: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int
)

@Serializable
data class CreatedCharacterResponse(
    val id: String,
    val name: String,
    @SerialName("class") val className: String,
    val race: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int
)

@Serializable
data class DeleteResponse(val message: String)


@Serializable
data class CharacterStatsResponse(
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val modifiers: Stats
)

@Serializable
data class UpdatedStatsResponse(
    val strength: Int,
    val strengthModifier: Int,
    val dexterity: Int,
    val dexterityModifier: Int,
    val constitution: Int,
    val constitutionModifier: Int,
    val intelligence: Int,
    val intelligenceModifier: Int,
    val wisdom: Int,
    val wisdomModifier: Int,
    val charisma: Int,
    val charismaModifier: Int,
)

@Serializable
data class CharacterSkillsResponse(
    val athletics: Int? = null,
    val acrobatics: Int? = null,
    val sleightOfHand: Int? = null,
    val stealth: Int? = null,
    val arcana: Int? = null,
    val history: Int? = null,
    val investigation: Int? = null,
    val nature: Int? = null,
    val religion: Int? = null,
    val animalHandling: Int? = null,
    val insight: Int? = null,
    val medicine: Int? = null,
    val perception: Int? = null,
    val survival: Int? = null,
    val deception: Int? = null,
    val intimidation: Int? = null,
    val performance: Int? = null,
    val persuasion: Int? = null,
    val trained: List<String>,
    val expertise: List<String>
)

@Serializable
data class StatsUpdateRequest(
    val strength: Int? = null,
    val dexterity: Int? = null,
    val constitution: Int? = null,
    val intelligence: Int? = null,
    val wisdom: Int? = null,
    val charisma: Int? = null
)

@Serializable
data class UpdateSkillRequest(
    val proficient: Boolean,
    val expertise: Boolean
)

@Serializable
data class UpdateSkillResponse(
    val skill: String,
    val ability: String,
    val modifier: Int,
    val proficient: Boolean,
    val expertise: Boolean,
    val totalBonus: Int
)

@Serializable
data class SavingThrows(
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val proficient: List<String>
)

@Serializable
data class MarkSavingThrowProficiencyRequest(val proficient: Boolean)

@Serializable
data class MarkSavingThrowProficiencyResponse(
    val ability: String,
    val modifier: Int,
    val proficient: Boolean,
    val totalBonus: Int
)
