package com.example.dndredactor.data.model

data class CharacterPresentation (
    val id: String,
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
    val description: String
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
    val raceDescription: String? = null,
    val subraceDescription: String? = null,
    val characterClass: CharacterClass? = null,
    val background: Background? = null
)