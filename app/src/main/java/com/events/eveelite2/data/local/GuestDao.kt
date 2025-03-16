package com.events.eveelite2.data.local

import androidx.room.*
import com.events.eveelite2.data.model.Guest
import kotlinx.coroutines.flow.Flow

@Dao
interface GuestDao {
    @Query("SELECT * FROM guests WHERE eventId = :eventId")
    fun getGuestsByEventId(eventId: Long): Flow<List<Guest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(guest: Guest): Long

    @Update
    suspend fun updateGuest(guest: Guest)

    @Delete
    suspend fun deleteGuest(guest: Guest)

    @Query("SELECT COUNT(*) FROM guests WHERE eventId = :eventId AND status = 'CONFIRMED'")
    suspend fun getConfirmedGuestCount(eventId: Long): Int
} 