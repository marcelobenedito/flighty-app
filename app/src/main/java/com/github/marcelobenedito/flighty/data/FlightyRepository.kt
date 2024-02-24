package com.github.marcelobenedito.flighty.data

import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Favorite
import com.github.marcelobenedito.flighty.data.model.Flight

interface FlightyRepository {
    suspend fun getFavorites(): List<Flight>
    suspend fun getFlightsByAirport(airportId: Int): List<Flight>
    suspend fun getAirportByCodeOrName(codeOrName: String): List<Airport>
    fun getAirportByCode(code: String): Airport
    suspend fun favoriteFlight(flight: Favorite)
    suspend fun getSearchText(): String
    suspend fun saveSearchText(searchText: String)
}