package com.github.marcelobenedito.flighty.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.marcelobenedito.flighty.data.model.Airline
import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Favorite
import com.github.marcelobenedito.flighty.data.model.Flight
import com.github.marcelobenedito.flighty.data.room.FlightyDao
import com.github.marcelobenedito.flighty.data.util.AirlineData
import kotlinx.coroutines.flow.first
import kotlin.math.abs
import kotlin.random.Random


class OfflineFlightyRepository(
    private val flightyDao: FlightyDao,
    private val dataStore: DataStore<Preferences>
) : FlightyRepository {
    override suspend fun getFavorites(): List<Flight> {
        return flightyDao.getFavorites().first().mapIndexed { index, favorite ->
            createFlight(
                id = index,
                departureAirport = flightyDao.getAirportByCode(favorite.departureCode),
                destinationAirport = flightyDao.getAirportByCode(favorite.destinationCode),
                isFavorite = true
            )
        }
    }

    override suspend fun getFlightsByAirport(airportId: Int): List<Flight> {
        val airports = flightyDao.getAllAirports().first().toMutableList()
        val targetAirport = airports.find { it.id == airportId }.also { airports.remove(it) }
            ?: throw IllegalArgumentException("Not found airport=$airportId")
        val favorites: List<Favorite> = flightyDao.getFavoritesByDepartureCode(targetAirport.code).first()


        return airports.mapIndexed { index, airport ->
            createFlight(
                id = index,
                departureAirport = targetAirport,
                destinationAirport = airport,
                isFavorite = favorites.any { favorite -> airport.code == favorite.destinationCode }
            )
        }
    }

    private fun createFlight(
        id: Int,
        departureAirport: Airport,
        destinationAirport: Airport,
        isFavorite: Boolean
    ): Flight {
        val startHour = (0..12).random()
        val startMinutes = (0..59).random()
        val endHour = (0..12).random()
        val endMinutes = (0..59).random()

        val startTimeMinutes = startHour * 60 + startMinutes
        val endTimeMinutes = endHour * 60 + endMinutes

        val timeDifferenceMinutes = if (endTimeMinutes >= startTimeMinutes)
            endTimeMinutes - startTimeMinutes
        else
            720 - abs(endTimeMinutes - startTimeMinutes)

        val hoursDifference = timeDifferenceMinutes / 60
        val minutesDifference = timeDifferenceMinutes % 60

        val airlines: List<Airline> = AirlineData.airlines
        val selectedAirline = airlines[airlines.indices.random()]
        val selectedAirplane = selectedAirline.airplanes[selectedAirline.airplanes.indices.random()]

        return Flight(
            id,
            departure = departureAirport,
            departureTime = "${startHour.toString().padStart(2, '0')}:${startMinutes.toString().padStart(2, '0')}",
            destination = destinationAirport,
            destinationTime = "${endHour.toString().padStart(2, '0')}:${endMinutes.toString().padStart(2, '0')}",
            airplane = selectedAirplane,
            drawableResourceId = selectedAirline.drawableResourceId,
            totalTime = "${hoursDifference.toString().padStart(2, '0')} hrs ${minutesDifference.toString().padStart(2, '0')} min",
            isWifiAvailable = Random.nextBoolean(),
            isMovieAvailable = Random.nextBoolean(),
            isMealAvailable = Random.nextBoolean(),
            isDrinkAvailable = Random.nextBoolean(),
            isFavorite = isFavorite
        )
    }

    override suspend fun getAirportByCodeOrName(codeOrName: String): List<Airport> {
        return flightyDao.getAirportsByCodeOrName(codeOrName).first()
    }

    override fun getAirportByCode(code: String): Airport {
        TODO("Not yet implemented")
    }

    override suspend fun favoriteFlight(flight: Favorite) {
        flightyDao.saveFavoriteFlight(flight)
    }

    override suspend fun getSearchText(): String {
        return dataStore.data.first().toPreferences()[SEARCH_TEXT_KEY] ?: ""
    }

    override suspend fun saveSearchText(searchText: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_TEXT_KEY] = searchText
        }
    }

    companion object {
        val SEARCH_TEXT_KEY = stringPreferencesKey("search_text_key")
    }
}