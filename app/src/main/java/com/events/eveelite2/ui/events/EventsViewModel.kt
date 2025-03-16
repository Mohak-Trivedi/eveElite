package com.events.eveelite2.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.events.eveelite2.data.model.Event
import com.events.eveelite2.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            eventRepository.getAllEvents()
                .catch { error ->
                    _uiState.value = EventsUiState.Error(error.message ?: "Unknown error")
                }
                .collect { events ->
                    _uiState.value = EventsUiState.Success(events)
                }
        }
    }

    fun createEvent(event: Event) {
        viewModelScope.launch {
            try {
                eventRepository.createEvent(event)
                loadEvents()
            } catch (e: Exception) {
                _uiState.value = EventsUiState.Error(e.message ?: "Failed to create event")
            }
        }
    }
}

sealed class EventsUiState {
    data object Loading : EventsUiState()
    data class Success(val events: List<Event>) : EventsUiState()
    data class Error(val message: String) : EventsUiState()
} 