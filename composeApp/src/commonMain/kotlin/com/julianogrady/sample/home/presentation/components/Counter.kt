package com.julianogrady.sample.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.julianogrady.sample.core.theming.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview(backgroundColor = 0xFFe2dfeb, showBackground = true)
fun CounterPreview() {
    Theme {
        Counter(
            value = 10,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
        )
    }
}

@Composable
fun Counter(value: Long, modifier: Modifier, onPlusClick: () -> Unit = {}, onMinusClick: () -> Unit = {}) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // simple counter with + and - buttons
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically // Centers children vertically
            ) {
                Button(
                    onClick = onMinusClick,
                    modifier = Modifier
                    .padding(16.dp)
                    .height(IntrinsicSize.Min),
                ) {
                    Text("-")
                }
                Text(
                    text = value.toString(),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .weight(1f),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
                Button(
                    onClick = onPlusClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .height(IntrinsicSize.Min),
                ) {
                    Text("+")
                }
            }
        }
    }
}