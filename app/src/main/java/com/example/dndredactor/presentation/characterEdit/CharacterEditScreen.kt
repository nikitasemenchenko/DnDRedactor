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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.dndredactor.presentation.components.CounterRow
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.components.Dropdown
import com.example.dndredactor.presentation.components.ReadOnlyInfo
import com.example.dndredactor.presentation.components.SelectableButton
import com.example.dndredactor.presentation.components.Title

@Composable
fun CharacterEditScreen(
    vm: CharacterEditViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSaved: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.events.collect { event ->
            when(event) {
                CharacterEditEvent.CharacterUpdated -> onSaved()
                is CharacterEditEvent.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.message.resId)
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
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
                        text = stringResource(state.message.resId),
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
            onValueChange = vm::onNameChanged,
            labelRes = R.string.character_name,
            minLines = 1
        )

        Title(R.string.character_gender)

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

        Title(R.string.character_level)

        CounterRow(
            title = stringResource(R.string.level),
            value = character.level.toString(),
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
                    stringResource(R.string.hide_race_and_class_change)
                } else {
                    stringResource(R.string.change_race_and_class)
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

        Title(R.string.backstory_selection)

        CustomTextField(
            value = character.backstory,
            onValueChange = vm::onBackstoryChanged,
            labelRes = R.string.backstory_placeholder,
            minLines = 6
        )

        Title(R.string.equipment_selection)

        CustomTextField(
            value = character.equipment,
            onValueChange = vm::onEquipmentChanged,
            labelRes = R.string.equipment_placeholder,
            minLines = 6
        )

        Title(R.string.appearance_traits)

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
            minLines = 2
        )

        CustomTextField(
            value = character.ideal,
            onValueChange = vm::onIdealChanged,
            labelRes = R.string.ideal,
            minLines = 2
        )

        CustomTextField(
            value = character.attachment,
            onValueChange = vm::onAttachmentChanged,
            labelRes = R.string.attachment,
            minLines = 2
        )

        CustomTextField(
            value = character.weakness,
            onValueChange = vm::onWeaknessChanged,
            labelRes = R.string.weakness,
            minLines = 2
        )

        Title(R.string.characteristics)

        Ability.entries.forEach { ability ->
            val abilityScore = character.abilityScores.get(ability)
            val modifier = calculateAbilityModifier(abilityScore)

            CounterRow(
                title = stringResource(ability.titleRes),
                value = "$abilityScore (${textAsModifier(modifier)})",
                onMinus = { vm.decreaseAbility(ability) },
                onPlus = { vm.increaseAbility(ability) }
            )
        }

        Title(R.string.additional_info_selection)

        CustomTextField(
            value = character.additionalInfo,
            onValueChange = vm::onAdditionalInfoChanged,
            labelRes = R.string.additional_info_placeholder,
            minLines = 6
        )
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