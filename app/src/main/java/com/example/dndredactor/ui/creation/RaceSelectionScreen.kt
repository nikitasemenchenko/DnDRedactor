package com.example.dndredactor.ui.creation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.ui.AppViewModelProvider
import com.example.dndredactor.ui.theme.ButtonColor
import com.example.dndredactor.ui.theme.LightButtonColor
import com.example.dndredactor.ui.theme.LightColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceSelectionScreen(
    vm: CreationViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNext: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = uiState.character.fullName,
            onValueChange = vm::onNameChanged,
            label = { Text(stringResource(R.string.character_name)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightColor,
                focusedContainerColor = LightColor,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Title(R.string.character_gender)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderButton(
                text = stringResource(R.string.male),
                isSelected = uiState.character.gender is Gender.MALE,
                onClick = { vm.onGenderSelected(Gender.MALE) },
                modifier = Modifier.weight(1f)
            )
            GenderButton(
                text = stringResource(R.string.female),
                isSelected = uiState.character.gender is Gender.FEMALE,
                onClick = { vm.onGenderSelected(Gender.FEMALE) },
                modifier = Modifier.weight(1f)
            )
        }

        Title(R.string.race_selection)
        RaceDropdown(
            races = uiState.races,
            selectedRaceId = uiState.character.raceId,
            onSelect = vm::onRaceSelected
        )
        DescriptionCard(
            desc = uiState.character.raceDescription,
            placeholder = R.string.race_desc_placeholder
        )

        val selectedRace = vm.getRaceById(uiState.character.raceId)
        if (selectedRace != null && selectedRace.subraces.isNotEmpty()) {
            val selectedSubrace = vm.getSubraceById(selectedRace, uiState.character.subraceId)
            Title(R.string.subrace_selection)
            SubraceDropdown(
                subraces = selectedRace.subraces,
                selectedSubraceId = uiState.character.subraceId,
                onSelect = vm::onSubraceSelected
            )
            DescriptionCard(
                desc = selectedSubrace?.description,
                placeholder = R.string.subrace_desc_placeholder
            )
        }


    }
}

@Composable
fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LightButtonColor else ButtonColor,
            contentColor = if (isSelected) Color.Black else LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text)
            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = text,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceDropdown(
    races: List<Race>,
    selectedRaceId: String?,
    onSelect: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = races.find { it.id == selectedRaceId }?.name ?: ""

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            readOnly = true,
            value = selected,
            onValueChange = {},
            label = { Text(stringResource(R.string.character_race)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightColor,
                focusedContainerColor = LightColor,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            races.forEach { race ->
                DropdownMenuItem(
                    text = { Text(race.name) },
                    onClick = {
                        onSelect(race.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubraceDropdown(
    subraces: List<Subrace>,
    selectedSubraceId: String?,
    onSelect: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = subraces.find { it.id == selectedSubraceId }?.name ?: ""
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.character_subrace)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightColor,
                focusedContainerColor = LightColor,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            subraces.forEach { s ->
                DropdownMenuItem(text = { Text(s.name) }, onClick = {
                    onSelect(s.id)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun DescriptionCard(
    desc: String?,
    placeholder: Int,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor,
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.animateContentSize()
    ) {
        Text(
            text = desc ?: stringResource(placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(8.dp),
            minLines = 3
        )
    }
}

@Composable
fun Title(textRes: Int) {
    Text(
        text = stringResource(textRes),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        color = LightColor
    )
}