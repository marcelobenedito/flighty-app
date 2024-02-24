package com.github.marcelobenedito.flighty.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.marcelobenedito.flighty.FlightyApplication
import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Favorite
import com.github.marcelobenedito.flighty.data.model.Flight
import com.github.marcelobenedito.flighty.data.FlightyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightyViewModel(private val flightyRepository: FlightyRepository): ViewModel() {

    private var _uiState = MutableStateFlow(FlightyUiState())
    var uiState: StateFlow<FlightyUiState> = _uiState

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            val searchText = flightyRepository.getSearchText()
            _uiState.update {
                it.copy(
                    searchText = searchText,
                    isDisplayFavorite = searchText.isBlank(),
                    favorites = flightyRepository.getFavorites(),
                    airports = flightyRepository.getAirportByCodeOrName(searchText)
                )
            }
        }
    }

    fun onEnterSearchText(searchText: String) {
        _uiState.update {
            it.copy(searchText = searchText, isDisplayFavorite = searchText.isBlank())
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(airports = flightyRepository.getAirportByCodeOrName(searchText))
            }
            flightyRepository.saveSearchText(searchText)
        }
    }

    fun selectAirport(airport: Airport) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedAirport = airport,
                    flights = flightyRepository.getFlightsByAirport(airport.id)
                )
            }
        }
    }

    fun favoriteFlight(selectedFlight: Flight) {
        val updatedFlights = _uiState.value.flights.map { flight ->
            if (selectedFlight.id == flight.id) selectedFlight.copy(
                isFavorite = !selectedFlight.isFavorite
            ) else flight
        }

        _uiState.update {
            it.copy(
                flights = updatedFlights,
                favorites = updatedFlights.filter { it.isFavorite }
            )
        }

        viewModelScope.launch {
            flightyRepository.favoriteFlight(Favorite(
                departureCode = selectedFlight.departure.code,
                destinationCode = selectedFlight.destination.code
            ))
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as FlightyApplication).appContainer
                FlightyViewModel(appContainer.flightyRepository)
            }
        }
    }
}

data class FlightyUiState(
    val searchText: String = "",
    val isDisplayFavorite: Boolean = false,
    val selectedAirport: Airport? = null,
    val airports: List<Airport> = emptyList(),
    val flights: List<Flight> = emptyList(),
    val favorites: List<Flight> = emptyList()
)
