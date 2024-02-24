package com.github.marcelobenedito.flighty.data.model

import androidx.annotation.DrawableRes

data class Airline(
    val name: String,
    val airplanes: List<String>,
    @DrawableRes val drawableResourceId: Int
)
