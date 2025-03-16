package com.events.eveelite2.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateTimePicker(
    dateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current
    val formatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm") }

    Button(
        onClick = {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            onDateTimeSelected(
                                LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                            )
                        },
                        dateTime.hour,
                        dateTime.minute,
                        true
                    ).show()
                },
                dateTime.year,
                dateTime.monthValue - 1,
                dateTime.dayOfMonth
            ).show()
        }
    ) {
        Text(dateTime.format(formatter))
    }
} 