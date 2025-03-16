package com.events.eveelite2.ui.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.events.eveelite2.data.model.Event
import com.events.eveelite2.data.model.Guest
import com.events.eveelite2.data.model.GuestStatus
import com.events.eveelite2.data.repository.EventRepository
import com.events.eveelite2.data.repository.GuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val guestRepository: GuestRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventId: Long = checkNotNull(savedStateHandle["eventId"])
    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState

    init {
        loadEventDetails()
    }

    private fun loadEventDetails() {
        viewModelScope.launch {
            try {
                combine(
                    guestRepository.getGuestsByEventId(eventId),
                    guestRepository.getConfirmedGuestCount(eventId).let { count -> 
                        MutableStateFlow(count) 
                    }
                ) { guests, confirmedCount ->
                    val event = eventRepository.getEventById(eventId)
                    if (event != null) {
                        EventDetailUiState.Success(
                            event = event,
                            guests = guests,
                            confirmedCount = confirmedCount
                        )
                    } else {
                        EventDetailUiState.Error("Event not found")
                    }
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = EventDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addGuest(guest: Guest) {
        viewModelScope.launch {
            try {
                guestRepository.addGuest(guest)
            } catch (e: Exception) {
                _uiState.value = EventDetailUiState.Error(e.message ?: "Failed to add guest")
            }
        }
    }

    fun updateGuestStatus(guest: Guest, newStatus: GuestStatus) {
        viewModelScope.launch {
            try {
                guestRepository.updateGuest(guest.copy(status = newStatus))
            } catch (e: Exception) {
                _uiState.value = EventDetailUiState.Error(e.message ?: "Failed to update guest status")
            }
        }
    }

    fun removeGuest(guest: Guest) {
        viewModelScope.launch {
            try {
                guestRepository.removeGuest(guest)
            } catch (e: Exception) {
                _uiState.value = EventDetailUiState.Error(e.message ?: "Failed to remove guest")
            }
        }
    }
}

sealed class EventDetailUiState {
    data object Loading : EventDetailUiState()
    data class Success(
        val event: Event,
        val guests: List<Guest>,
        val confirmedCount: Int
    ) : EventDetailUiState()
    data class Error(val message: String) : EventDetailUiState()
} 