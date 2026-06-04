package com.example.dndredactor.presentation.creation.logic

import com.example.dndredactor.data.model.AbilityGenerationMethod
import com.example.dndredactor.data.model.CharacterDraft
import com.example.dndredactor.presentation.creation.CreationStep

object CreationStepNavigator {

    fun getNextStep(
        currentStep: CreationStep,
        character: CharacterDraft
    ): CreationStep {
        return when (currentStep) {
            CreationStep.IDENTITY -> CreationStep.STORY
            CreationStep.STORY -> CreationStep.RACE
            CreationStep.RACE -> CreationStep.CLASS
            CreationStep.CLASS -> CreationStep.ABILITY_GENERATION_METHOD

            CreationStep.ABILITY_GENERATION_METHOD -> {
                when (character.abilityGenerationMethod) {
                    AbilityGenerationMethod.RANDOM -> CreationStep.RANDOM_ABILITIES
                    AbilityGenerationMethod.POINT_BUY -> CreationStep.POINT_BUY_ABILITIES
                    null -> CreationStep.ABILITY_GENERATION_METHOD
                }
            }

            CreationStep.RANDOM_ABILITIES -> CreationStep.COMBAT_STATS
            CreationStep.POINT_BUY_ABILITIES -> CreationStep.COMBAT_STATS
            CreationStep.COMBAT_STATS -> CreationStep.EQUIPMENT
            CreationStep.EQUIPMENT -> CreationStep.ADDITIONAL_INFO
            CreationStep.ADDITIONAL_INFO -> CreationStep.FINAL
            CreationStep.FINAL -> CreationStep.FINAL
        }
    }

    fun getPreviousStep(
        currentStep: CreationStep,
        character: CharacterDraft
    ): CreationStep {
        return when (currentStep) {
            CreationStep.IDENTITY -> CreationStep.IDENTITY
            CreationStep.STORY -> CreationStep.IDENTITY
            CreationStep.RACE -> CreationStep.STORY
            CreationStep.CLASS -> CreationStep.RACE
            CreationStep.ABILITY_GENERATION_METHOD -> CreationStep.CLASS
            CreationStep.RANDOM_ABILITIES -> CreationStep.ABILITY_GENERATION_METHOD
            CreationStep.POINT_BUY_ABILITIES -> CreationStep.ABILITY_GENERATION_METHOD

            CreationStep.COMBAT_STATS -> {
                when (character.abilityGenerationMethod) {
                    AbilityGenerationMethod.RANDOM -> CreationStep.RANDOM_ABILITIES
                    AbilityGenerationMethod.POINT_BUY -> CreationStep.POINT_BUY_ABILITIES
                    null -> CreationStep.ABILITY_GENERATION_METHOD
                }
            }

            CreationStep.EQUIPMENT -> CreationStep.COMBAT_STATS
            CreationStep.ADDITIONAL_INFO -> CreationStep.EQUIPMENT
            CreationStep.FINAL -> CreationStep.ADDITIONAL_INFO
        }
    }
}