package com.github.marcelobenedito.flighty.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightyDao {

    @Query("SELECT * FROM airport WHERE iata_code LIKE :code")
    fun getAirportByCode(code: String): Airport

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE departure_code LIKE :code")
    fun getFavoritesByDepartureCode(code: String): Flow<List<Favorite>>

    @Query("SELECT * FROM airport WHERE iata_code LIKE :codeOrName || '%' OR name LIKE :codeOrName || '%' ORDER BY passengers DESC")
    fun getAirportsByCodeOrName(codeOrName: String): Flow<List<Airport>>
    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAllAirports(): Flow<List<Airport>>
    @Insert
    suspend fun saveFavoriteFlight(favorite: Favorite)
}