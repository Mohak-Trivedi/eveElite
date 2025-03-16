package com.events.eveelite2.data.repository

import com.events.eveelite2.data.local.GuestDao
import com.events.eveelite2.data.model.Guest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GuestRepository @Inject constructor(
    private val guestDao: GuestDao
) {
    fun getGuestsByEventId(eventId: Long): Flow<List<Guest>> = 
        guestDao.getGuestsByEventId(eventId)

    suspend fun addGuest(guest: Guest): Long = guestDao.insertGuest(guest)

    suspend fun updateGuest(guest: Guest) = guestDao.updateGuest(guest)

    suspend fun removeGuest(guest: Guest) = guestDao.deleteGuest(guest)

    suspend fun getConfirmedGuestCount(eventId: Long): Int = 
        guestDao.getConfirmedGuestCount(eventId)
} 