package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightColor

// заглушка
@Composable
fun CreationSummaryScreen(
    vm: CreationViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState by vm.uiState.collectAsState()
        val character = uiState.character

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Проверьте персонажа",
                color = LightColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            SummaryBlock(title = "Основное") {
                SummaryLine("Имя", character.fullName)
                SummaryLine("Уровень", character.level.toString())
                SummaryLine("Раса", character.raceName ?: "Не выбрана")
                SummaryLine("Подраса", character.subraceName ?: "Не выбрана")
                SummaryLine("Класс", character.className ?: "Не выбран")
                SummaryLine("Архетип", character.archetypeName ?: "Не выбран")
                SummaryLine(
                    key = "Бонус мастерства",
                    value = textAsModifier(calculateProficiencyBonus(character.level))
                )
            }

            SummaryBlock(title = "Предыстория") {
                SummaryLine(
                    key = "Описание",
                    value = character.backstory
                )
            }

            SummaryBlock(title =  "Характеристики") {
                Ability.entries.forEach { ability ->
                    val score = character.abilityScores.get(ability)
                    val modifier = character.abilityScores.getModifier(ability)

                    SummaryLine(
                        key = ability.title,
                        value = "$score (${textAsModifier(modifier)})"
                    )
                }
            }

            SummaryBlock(title = "Боевые показатели") {
                SummaryLine(
                    key = "Класс доспеха",
                    value = character.armorClass.toString()
                )
                SummaryLine(
                    key = "HP",
                    value = "${character.currentHitPoints}/${character.maxHitPoints}"
                )
            }

            SummaryBlock(title = "Снаряжение") {
                SummaryLine(
                    key = "Предметы",
                    value = character.equipment
                )
            }

            SummaryBlock(title = "Дополнительные сведения") {
                SummaryLine(
                    key = "Описание",
                    value = character.additionalInfo
                )
            }
        }
    }
}

@Composable
fun SummaryLine(
    key: String,
    value: String
){
    Text(
        text = "$key: ${value.ifBlank { "Не указано" }}",
        color = LightColor,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun SummaryBlock(
    title: String,
    content: @Composable () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = LightColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        content()
    }
}