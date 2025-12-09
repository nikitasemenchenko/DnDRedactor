package com.example.dndredactor.ui.mainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.data.model.CharacterPresentation
import com.example.dndredactor.data.model.getClassIcon
import com.example.dndredactor.ui.AppViewModelProvider
import com.example.dndredactor.ui.theme.BackPurple
import com.example.dndredactor.ui.theme.ButtonColor
import com.example.dndredactor.ui.theme.LightColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onCharacterClick: (String) -> Unit,
    onCreateClick: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.d20),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.russian_app_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = LightColor,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { vm.onLogout() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(R.string.exit),
                            tint = LightColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackPurple,
                    titleContentColor = LightColor
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCreateClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(R.string.create),
                    style = MaterialTheme.typography.headlineSmall,
                    color = LightColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is MainScreenUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MainScreenUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource((uiState as MainScreenUiState.Error).message),
                            color = LightColor,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                is MainScreenUiState.Success -> {
                    CharacterList(
                        characters = (uiState as MainScreenUiState.Success).characters,
                        onDelete = { id -> vm.deleteCharacter(id) },
                        onCharacterClick = onCharacterClick
                    )
                }

                is MainScreenUiState.LoggedOut -> {
                    onLogout()
                }
            }
        }
    }
}

@Composable
fun CharacterList(
    characters: List<CharacterPresentation>,
    onDelete: (String) -> Unit,
    onCharacterClick: (String) -> Unit
) {
    val firstLoad = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(100)
        firstLoad.value = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = characters,
            key = { it.id }
        ) { character ->

            AnimatedVisibility(
                visible = !firstLoad.value,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(800)
                ) + fadeIn(),
            ) {

                CharacterCard(
                    character = character,
                    onClick = { onCharacterClick(character.id) },
                    onDelete = { onDelete(character.id) }
                )
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: CharacterPresentation,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val classIcon = getClassIcon(character.characterClass)

    AnimatedVisibility(
        visible = visible,
        exit = fadeOut(animationSpec = tween(300))
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = LightColor
            ),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(classIcon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        visible = false
                        scope.launch {
                            delay(300)
                            onDelete()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
    }
}