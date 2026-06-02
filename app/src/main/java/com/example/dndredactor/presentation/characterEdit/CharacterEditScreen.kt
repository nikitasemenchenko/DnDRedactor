package com.example.dndredactor.presentation.characterEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.calculateAbilityModifier
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.creation.steps.CustomTextField
import com.example.dndredactor.presentation.creation.steps.Dropdown
import com.example.dndredactor.presentation.creation.steps.GenderButton
import com.example.dndredactor.presentation.creation.steps.Title

@Composable
fun CharacterEditScreen(
    vm: CharacterEditViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSaved: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.events.collect { event ->
            when(event) {
                CharacterEditEvent.CharacterUpdated -> onSaved()
                is CharacterEditEvent.ShowError -> {
                    //Snackbar добавить
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CharacterEditTopBar(onBack = onBack)
        },
        bottomBar = {
            if(uiState is CharacterEditUiState.Success){
                CharacterEditBottomBar(
                    onBack = onBack,
                    onSave = vm::saveCharacter
                )
            }
        },
        containerColor = BackPurple
    ) { contentPadding ->
        when(val state = uiState) {
            CharacterEditUiState.Loading ->{
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }

            }
            is CharacterEditUiState.Success ->{
                CharacterEditContent(
                    modifier = Modifier.padding(contentPadding),
                    state = state,
                    vm = vm
                )
            }
            is CharacterEditUiState.Error ->{
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        color = LightColor,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterEditTopBar(
    onBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.edit_character),
                color = LightColor,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = LightColor
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackPurple
        )
    )
}

@Composable
fun CharacterEditBottomBar(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Surface(
        color = BackPurple,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text(stringResource(R.string.cancel), color = LightColor)
            }

            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text(stringResource(R.string.save), color = LightColor)
            }
        }
    }
}

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
        Title(R.string.main)
        CustomTextField(
            value = character.name,
            onChange = vm::onNameChanged,
            labelRes = R.string.character_name,
            minLines = 1
        )

        Title(R.string.character_gender)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderButton(
                text = stringResource(R.string.male),
                isSelected = character.gender == Gender.MALE,
                onClick = { vm.onGenderChanged(Gender.MALE) }
            )

            GenderButton(
                text = stringResource(R.string.female),
                isSelected = character.gender == Gender.FEMALE,
                onClick = { vm.onGenderChanged(Gender.FEMALE) }
            )
        }

        Title(R.string.character_level)

        LevelEdit(
            level = character.level,
            onMinus = vm::decreaseLevel,
            onPlus = vm::increaseLevel
        )

        ReadOnlyInfo(
            title = stringResource(R.string.proficiency_bonus),
            value = textAsModifier(calculateProficiencyBonus(character.level))
        )

        ReadOnlyInfo(
            title = stringResource(R.string.race),
            value = character.raceName ?: stringResource(R.string.not_selected)
        )

        ReadOnlyInfo(
            title = stringResource(R.string.subrace),
            value = character.subraceName ?: stringResource(R.string.not_selected)
        )

        ReadOnlyInfo(
            title = stringResource(R.string.class_name),
            value = character.className ?: stringResource(R.string.not_selected)
        )

        ReadOnlyInfo(
            title = stringResource(R.string.archetype),
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
                    stringResource(R.string.change_race_and_class)
                } else {
                    stringResource(R.string.hide_race_and_class_change)
                },
                color = LightColor
            )
        }

        if (state.coreEditEnabled) {
            CharacterCoreEditBlock(
                state = state,
                vm = vm
            )
        }

        EditableNumberRow(
            title = stringResource(R.string.armor_class),
            value = character.armorClass.toString(),
            onMinus = vm::decreaseArmorClass,
            onPlus = vm::increaseArmorClass
        )

        EditableNumberRow(
            title = stringResource(R.string.max_hit_points),
            value = character.maxHitPoints.toString(),
            onMinus = vm::decreaseMaxHitPoints,
            onPlus = vm::increaseMaxHitPoints
        )

        EditableNumberRow(
            title = stringResource(R.string.current_hit_points),
            value = character.currentHitPoints.toString(),
            onMinus = vm::decreaseCurrentHitPoints,
            onPlus = vm::increaseCurrentHitPoints
        )

        Title(R.string.backstory_selection)

        CustomTextField(
            value = character.backstory,
            onChange = vm::onBackstoryChanged,
            labelRes = R.string.backstory_placeholder,
            minLines = 6
        )

        Title(R.string.equipment_selection)

        CustomTextField(
            value = character.equipment,
            onChange = vm::onEquipmentChanged,
            labelRes = R.string.equipment_placeholder,
            minLines = 6
        )

        Title(R.string.appearance_traits)

        CustomTextField(
            value = character.appearance,
            onChange = vm::onAppearanceChanged,
            labelRes = R.string.appearance_placeholder,
            minLines = 3
        )

        CustomTextField(
            value = character.personality,
            onChange = vm::onPersonalityChanged,
            labelRes = R.string.personality,
            minLines = 2
        )

        CustomTextField(
            value = character.ideal,
            onChange = vm::onIdealChanged,
            labelRes = R.string.ideal,
            minLines = 2
        )

        CustomTextField(
            value = character.attachment,
            onChange = vm::onAttachmentChanged,
            labelRes = R.string.attachment,
            minLines = 2
        )

        CustomTextField(
            value = character.weakness,
            onChange = vm::onWeaknessChanged,
            labelRes = R.string.weakness,
            minLines = 2
        )

        Title(R.string.characteristics)

        Ability.entries.forEach { ability ->
            AbilityEditRow(
                title = stringResource(ability.titleRes),
                value = character.abilityScores.get(ability),
                onMinus = { vm.decreaseAbility(ability) },
                onPlus = { vm.increaseAbility(ability) }
            )
        }

        Title(R.string.additional_info_selection)

        CustomTextField(
            value = character.additionalInfo,
            onChange = vm::onAdditionalInfoChanged,
            labelRes = R.string.additional_info_placeholder,
            minLines = 6
        )
    }
}


@Composable
fun ReadOnlyInfo(
    title: String,
    value: String
) {
    Text(
        text = "$title: $value",
        color = LightColor,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun AbilityEditRow(
    title: String,
    value: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onMinus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("-", color = LightColor)
            }

            Text(
                text = "$value (${textAsModifier(calculateAbilityModifier(value))})",
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = onPlus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("+", color = LightColor)
            }
        }
    }
}

@Composable
fun CharacterCoreEditBlock(
    state: CharacterEditUiState.Success,
    vm: CharacterEditViewModel
) {
    val character = state.character

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.character_main_characteristics),
            color = LightColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        if (state.coreLoading) {
            CircularProgressIndicator()
            return@Column
        }

        Dropdown(
            items = state.races,
            selectedId = character.raceId,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.character_race,
            onSelect = vm::onRaceSelected
        )

        if (state.raceDetailsLoading) {
            CircularProgressIndicator()
        }

        val selectedRace = state.races.find { it.id == character.raceId }

        if (selectedRace != null && selectedRace.subraces.isNotEmpty()) {
            Dropdown(
                items = selectedRace.subraces,
                selectedId = character.subraceId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.character_subrace,
                onSelect = vm::onSubraceSelected
            )
        }

        Dropdown(
            items = state.classes,
            selectedId = character.classId,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.class_selection,
            onSelect = vm::onClassSelected
        )

        if (state.classDetailsLoading) {
            CircularProgressIndicator()
        }

        val selectedClass = state.classes.find { it.id == character.classId }

        if (selectedClass != null && selectedClass.archetypes.isNotEmpty()) {
            Dropdown(
                items = selectedClass.archetypes,
                selectedId = character.archetypeId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.archetype_selection,
                onSelect = vm::onArchetypeSelected
            )
        }
    }
}

@Composable
fun LevelEdit(
    level: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Уровень",
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onMinus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("-", color = LightColor)
            }

            Text(
                text = level.toString(),
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = onPlus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("+", color = LightColor)
            }
        }
    }
}

@Composable
fun EditableNumberRow(
    title: String,
    value: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onMinus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("-", color = LightColor)
            }

            Text(
                text = value,
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = onPlus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("+", color = LightColor)
            }
        }
    }
}