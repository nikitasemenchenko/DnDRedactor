package com.example.dndredactor.data.model

data class CharacterPresentation (
    val id: Int,
    val name: String,
    val level: Int,
    val characterClass: Classes
)

sealed class Gender {
    object MALE : Gender()
    object FEMALE : Gender()
    object UNSPECIFIED : Gender()
}

data class Subrace(
    val id: String,
    val name: String,
    val description: String = ""
)

data class Race(
    val id: String,
    val name: String,
    val description: String = "",
    val subraces: List<Subrace> = emptyList()
)

data class CharacterClass(
    val id: String,
    val name: String,
    val description: String = "",
    val archetypes: List<Archetype> = emptyList()
)

data class Archetype(
    val id: String,
    val name: String,
    val description: String = ""
)

data class Background(
    val id: String,
    val name: String,
    val description: String
)

data class NewCharacter(
    val fullName: String = "",
    val gender: Gender = Gender.UNSPECIFIED,
    val raceId: String? = null,
    val subraceId: String? = null,
    val classId: String? = null,
    val archetypeId: String? = null,
    val appearance: String = "",
    val character: String = "",
    val ideal: String = "",
    val attachment: String = "",
    val weakness: String = "",
    val backgroundId: String? = null
)