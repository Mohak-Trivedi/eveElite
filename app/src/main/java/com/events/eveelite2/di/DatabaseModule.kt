package com.events.eveelite2.di

import android.content.Context
import androidx.room.Room
import com.events.eveelite2.data.local.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): EventDatabase = Room.databaseBuilder(
        context,
        EventDatabase::class.java,
        "event_database"
    ).build()

    @Provides
    fun provideEventDao(database: EventDatabase) = database.eventDao()

    @Provides
    fun provideGuestDao(database: EventDatabase) = database.guestDao()
} 