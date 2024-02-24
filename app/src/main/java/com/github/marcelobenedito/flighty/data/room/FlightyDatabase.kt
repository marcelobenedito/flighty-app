package com.github.marcelobenedito.flighty.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Favorite

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightyDatabase: RoomDatabase() {

    abstract fun flightyDao(): FlightyDao

    companion object {
        @Volatile
        private var Instance: FlightyDatabase? = null

        fun getDatabase(context: Context): FlightyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightyDatabase::class.java, "flighty.db")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}