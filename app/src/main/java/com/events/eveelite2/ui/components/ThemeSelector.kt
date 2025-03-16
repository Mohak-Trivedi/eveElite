package com.events.eveelite2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class EventTheme(val displayName: String, val primaryColor: Long) {
    DEFAULT("Default", 0xFF6200EE),
    BIRTHDAY("Birthday", 0xFFE91E63),
    WEDDING("Wedding", 0xFFF06292),
    CONFERENCE("Conference", 0xFF2196F3),
    PARTY("Party", 0xFF9C27B0),
    MEETING("Meeting", 0xFF4CAF50)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelector(
    selectedTheme: EventTheme,
    onThemeSelected: (EventTheme) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(EventTheme.values()) { theme ->
            FilterChip(
                selected = theme == selectedTheme,
                onClick = { onThemeSelected(theme) },
                label = { Text(theme.displayName) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
} 