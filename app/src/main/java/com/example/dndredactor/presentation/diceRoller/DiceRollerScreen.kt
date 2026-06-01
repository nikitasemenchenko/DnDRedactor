package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.R
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun DiceRollScreen(
    vm: DiceRollerViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            DiceRollerTopBar(
                onBack = onBack
            )
        },
        bottomBar = {
            DiceRollerBottomBar(
                onRollClick = vm::rollDice
            )
        },
        containerColor = BackPurple
    ) { contentPadding ->
        DiceRollerContent(
            modifier = Modifier.padding(contentPadding),
            state = uiState,
            vm = vm
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollerTopBar(
    onBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Бросок кубика",
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
fun DiceRollerBottomBar(
    onRollClick: () -> Unit
) {
    Surface(
        color = BackPurple,
        tonalElevation = 4.dp
    ) {
        Button(
            onClick = onRollClick,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = "Бросить",
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun DiceRollerContent(
    modifier: Modifier = Modifier,
    state: DiceRollerUiState,
    vm: DiceRollerViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Выберите кубик",
            color = LightColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(4,6,8,10,12,20,100).forEach { sides ->
                DiceTypeButton(
                    text = "d$sides",
                    isSelected = state.selectedDiceSides == sides,
                    onClick = {
                        vm.selectDice(sides)
                    }
                )
            }
        }

        CounterCard(
            title = "Количество кубиков",
            value = state.diceCount.toString(),
            onMinus = vm::decreaseDiceCount,
            onPlus = vm::increaseDiceCount
        )

        CounterCard(
            title = "Модификатор",
            value = if (state.modifier >= 0) "+${state.modifier}" else state.modifier.toString(),
            onMinus = vm::decreaseModifier,
            onPlus = vm::increaseModifier
        )

        LastResultCard(result = state.lastResult)

        if (state.history.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "История бросков",
                    color = LightColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                TextButton(
                    onClick = vm::clearHistory
                ) {
                    Text(
                        text = "Очистить",
                        color = LightColor
                    )
                }
            }

            state.history.forEach { result ->
                HistoryItem(result = result)
            }
        }
    }
}

@Composable
fun DiceTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LightButtonColor else ButtonColor,
            contentColor = if (isSelected) Color.Black else LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text)
    }
}

@Composable
fun CounterCard(
    title: String,
    value: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
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
                    color = Color.Black,
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
}

@Composable
fun LastResultCard(
    result: DiceRollResult?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Результат",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (result == null) {
                Text(
                    text = "Бросьте кубики",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    text = result.getResults(),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = result.total.toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Кубики: ${result.rolls.joinToString()}",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    result: DiceRollResult
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${result.getResults()} = ${result.total}",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Броски: ${result.rolls.joinToString()}",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}