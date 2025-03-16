package com.events.eveelite2.ui.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.events.eveelite2.data.model.Event
import java.time.LocalDateTime
import com.events.eveelite2.ui.components.DateTimePicker
import com.events.eveelite2.ui.components.EventTheme
import com.events.eveelite2.ui.components.ThemeSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventDialog(
    onDismiss: () -> Unit,
    onCreateEvent: (Event) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var maxCapacity by remember { mutableStateOf("100") }
    var selectedTheme by remember { mutableStateOf(EventTheme.DEFAULT) }
    var eventDateTime by remember { mutableStateOf(LocalDateTime.now().plusDays(7)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Event") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Event Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = maxCapacity,
                    onValueChange = { maxCapacity = it },
                    label = { Text("Max Capacity") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Event Date & Time",
                    style = MaterialTheme.typography.titleMedium
                )
                DateTimePicker(
                    dateTime = eventDateTime,
                    onDateTimeSelected = { eventDateTime = it }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium
                )
                ThemeSelector(
                    selectedTheme = selectedTheme,
                    onThemeSelected = { selectedTheme = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val event = Event(
                        name = name,
                        description = description,
                        date = eventDateTime,
                        location = location,
                        maxCapacity = maxCapacity.toIntOrNull() ?: 100,
                        theme = selectedTheme.name,
                        bannerUrl = null
                    )
                    onCreateEvent(event)
                    onDismiss()
                },
                enabled = name.isNotBlank() && location.isNotBlank()
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 