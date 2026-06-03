package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.dndredactor.R
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.LightColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    @StringRes titleRes: Int,
    onBack: (() -> Unit)? = null,
    actionIcon: ImageVector? = null,
    @StringRes actionContentDescriptionRes: Int? = null,
    onActionClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(titleRes),
                color = LightColor,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = LightColor
                    )
                }
            }
        },
        actions = {
            if (
                actionIcon != null &&
                actionContentDescriptionRes != null &&
                onActionClick != null
            ) {
                IconButton(
                    onClick = onActionClick
                ) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = stringResource(actionContentDescriptionRes),
                        tint = LightColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackPurple
        )
    )
}