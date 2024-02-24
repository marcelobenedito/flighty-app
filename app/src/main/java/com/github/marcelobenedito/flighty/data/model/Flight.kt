package com.github.marcelobenedito.flighty.data.model

import androidx.annotation.DrawableRes

data class Flight(
    val id: Int,
    val departure: Airport,
    val departureTime: String,
    val destination: Airport,
    val destinationTime: String,
    val airplane: String,
    @DrawableRes val drawableResourceId: Int,
    val totalTime: String,
    val isWifiAvailable: Boolean = false,
    val isMovieAvailable: Boolean = false,
    val isMealAvailable: Boolean = false,
    val isDrinkAvailable: Boolean = false,
    val isFavorite: Boolean = false,
)
