package com.example.dndredactor.presentation.characterEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.calculateAbilityModifier
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.components.CounterRow
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.components.SelectableButton
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterEditContent(
    modifier: Modifier = Modifier,
    state: CharacterEditUiState.Success,
    vm: CharacterEditViewModel
) {
    val character = state.character

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CharacterEditSection(R.string.main) {
            CustomTextField(
                value = character.name,
                onValueChange = vm::onNameChanged,
                labelRes = R.string.character_name,
                minLines = 1
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableButton(
                    text = stringResource(R.string.male),
                    isSelected = character.gender == Gender.MALE,
                    onClick = { vm.onGenderChanged(Gender.MALE) }
                )

                SelectableButton(
                    text = stringResource(R.string.female),
                    isSelected = character.gender == Gender.FEMALE,
                    onClick = { vm.onGenderChanged(Gender.FEMALE) }
                )
            }

            CounterRow(
                title = stringResource(R.string.level),
                value = character.level.toString(),
                onMinus = vm::decreaseLevel,
                onPlus = vm::increaseLevel
            )

            CharacterEditInfoRow(
                titleRes = R.string.proficiency_bonus,
                value = textAsModifier(calculateProficiencyBonus(character.level))
            )

            CharacterEditInfoRow(
                titleRes = R.string.race,
                value = character.raceName ?: stringResource(R.string.not_selected)
            )

            CharacterEditInfoRow(
                titleRes = R.string.subrace,
                value = character.subraceName ?: stringResource(R.string.not_selected)
            )

            CharacterEditInfoRow(
                titleRes = R.string.class_name,
                value = character.className ?: stringResource(R.string.not_selected)
            )

            CharacterEditInfoRow(
                titleRes = R.string.archetype,
                value = character.archetypeName ?: stringResource(R.string.not_selected)
            )

            Button(
                onClick = vm::onCoreEditClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = if (state.coreEditEnabled) {
                        stringResource(R.string.hide_race_and_class_change)
                    } else {
                        stringResource(R.string.change_race_and_class)
                    },
                    color = LightColor
                )
            }

            if (state.coreEditEnabled) {
                CharacterEditCoreBlock(
                    state = state,
                    vm = vm
                )
            }
        }

        CharacterEditSection(R.string.combat_stats_selection) {
            CounterRow(
                title = stringResource(R.string.armor_class),
                value = character.armorClass.toString(),
                onMinus = vm::decreaseArmorClass,
                onPlus = vm::increaseArmorClass
            )

            CounterRow(
                title = stringResource(R.string.max_hit_points),
                value = character.maxHitPoints.toString(),
                onMinus = vm::decreaseMaxHitPoints,
                onPlus = vm::increaseMaxHitPoints
            )

            CounterRow(
                title = stringResource(R.string.current_hit_points),
                value = character.currentHitPoints.toString(),
                onMinus = vm::decreaseCurrentHitPoints,
                onPlus = vm::increaseCurrentHitPoints
            )
        }

        CharacterEditSection(R.string.backstory_selection) {
            CustomTextField(
                value = character.backstory,
                onValueChange = vm::onBackstoryChanged,
                labelRes = R.string.backstory_placeholder,
                minLines = 6
            )
        }

        CharacterEditSection(R.string.equipment_selection) {
            CustomTextField(
                value = character.equipment,
                onValueChange = vm::onEquipmentChanged,
                labelRes = R.string.equipment_placeholder,
                minLines = 6
            )
        }

        CharacterEditSection(R.string.appearance_traits) {
            CustomTextField(
                value = character.appearance,
                onValueChange = vm::onAppearanceChanged,
                labelRes = R.string.appearance_placeholder,
                minLines = 3
            )

            CustomTextField(
                value = character.personality,
                onValueChange = vm::onPersonalityChanged,
                labelRes = R.string.personality,
                minLines = 3
            )

            CustomTextField(
                value = character.ideal,
                onValueChange = vm::onIdealChanged,
                labelRes = R.string.ideal,
                minLines = 3
            )

            CustomTextField(
                value = character.attachment,
                onValueChange = vm::onAttachmentChanged,
                labelRes = R.string.attachment,
                minLines = 3
            )

            CustomTextField(
                value = character.weakness,
                onValueChange = vm::onWeaknessChanged,
                labelRes = R.string.weakness,
                minLines = 3
            )
        }

        CharacterEditSection(R.string.characteristics) {
            Ability.entries.forEach { ability ->
                val abilityScore = character.abilityScores.get(ability)
                val modifier = calculateAbilityModifier(abilityScore)

                CounterRow(
                    title = stringResource(ability.titleRes),
                    value = stringResource(
                        R.string.ability_score_value,
                        abilityScore,
                        textAsModifier(modifier)
                    ),
                    onMinus = { vm.decreaseAbility(ability) },
                    onPlus = { vm.increaseAbility(ability) }
                )
            }
        }

        CharacterEditSection(R.string.additional_info_selection) {
            CustomTextField(
                value = character.additionalInfo,
                onValueChange = vm::onAdditionalInfoChanged,
                labelRes = R.string.additional_info_placeholder,
                minLines = 6
            )
        }
    }
}