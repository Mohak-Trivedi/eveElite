package com.events.eveelite2.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "guests",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Guest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventId: Long,
    val name: String,
    val email: String,
    val status: GuestStatus,
    val customFields: Map<String, String> = emptyMap()
)

enum class GuestStatus {
    PENDING,
    CONFIRMED,
    DECLINED,
    CANCELLED
} 