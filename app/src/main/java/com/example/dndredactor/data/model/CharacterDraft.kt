package com.example.dndredactor.data.model

data class CharacterDraft(
    val fullName: String = "",
    val gender: Gender = Gender.UNSPECIFIED,

    val raceId: String? = null,
    val subraceId: String? = null,

    val classId: String? = null,
    val archetypeId: String? = null,

    val appearance: String = "",
    val personality: String = "",
    val ideal: String = "",
    val attachment: String = "",
    val weakness: String = "",

    val abilityGenerationMethod: AbilityGenerationMethod? = null,
    val abilityScores: AbilityScores = AbilityScores(),

    val backgroundId: String? = null
)