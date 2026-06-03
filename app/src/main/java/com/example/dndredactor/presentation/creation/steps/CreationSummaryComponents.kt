package com.example.dndredactor.presentation.creation.steps

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomCard
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark

@Composable
fun SummaryCard(
    @StringRes titleRes: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    CustomCard {
        Text(
            text = stringResource(titleRes),
            color = TextPrimaryDark,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SummaryRow(
    @StringRes labelRes: Int,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = stringResource(labelRes),
            color = TextSecondaryDark,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value.ifBlank {
                stringResource(R.string.not_specified)
            },
            color = TextPrimaryDark,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SummaryTextSection(
    @StringRes titleRes: Int,
    text: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(titleRes),
            color = TextSecondaryDark,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = text.ifBlank {
                stringResource(R.string.not_specified)
            },
            color = TextPrimaryDark,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}