package com.events.eveelite2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.events.eveelite2.data.model.Event
import com.events.eveelite2.data.model.Guest

@Database(
    entities = [Event::class, Guest::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun guestDao(): GuestDao
} 