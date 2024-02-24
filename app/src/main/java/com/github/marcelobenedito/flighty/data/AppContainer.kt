package com.github.marcelobenedito.flighty.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.marcelobenedito.flighty.data.room.FlightyDatabase

interface AppContainer {
    val flightyRepository: FlightyRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "search_preferences"
    )

    private val flightyDatabase: FlightyDatabase by lazy {
        FlightyDatabase.getDatabase(context)
    }

    override val flightyRepository: FlightyRepository by lazy {
        OfflineFlightyRepository(flightyDatabase.flightyDao(), context.dataStore)
    }
}
