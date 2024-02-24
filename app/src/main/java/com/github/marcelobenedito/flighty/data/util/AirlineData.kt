package com.github.marcelobenedito.flighty.data.util

import com.github.marcelobenedito.flighty.R
import com.github.marcelobenedito.flighty.data.model.Airline

object AirlineData {
    val airlines = listOf(
        Airline("American Airlines", listOf("Boeing 737", "Boeing 777", "Airbus A320"), R.drawable.american_airlines),
        Airline("Delta Air Lines", listOf("Airbus A220", "Boeing 737", "Airbus A350"), R.drawable.delta_airlines),
        Airline("United Airlines", listOf("Boeing 787", "Airbus A320", "Boeing 777"), R.drawable.united_airlines),
        Airline("Southwest Airlines", listOf("Boeing 737"), R.drawable.southwest_airlines),
        Airline("Lufthansa", listOf("Airbus A320", "Boeing 747", "Airbus A350"), R.drawable.lufthansa),
        Airline("Emirates", listOf("Airbus A380", "Boeing 777"), R.drawable.emirates),
        Airline("British Airways", listOf("Airbus A320", "Boeing 777", "Airbus A350"), R.drawable.british_airways),
        Airline("Air France", listOf("Airbus A320", "Airbus A380", "Boeing 777"), R.drawable.air_france),
        Airline("Qatar Airways", listOf("Boeing 787", "Airbus A350", "Boeing 777"), R.drawable.qatar_airways),
        Airline("Singapore Airlines", listOf("Airbus A380", "Boeing 777", "Airbus A350"), R.drawable.singapore_airlines),
        Airline("Cathay Pacific", listOf("Airbus A350", "Boeing 777"), R.drawable.cathay_pacific),
        Airline("Qantas Airways", listOf("Boeing 787", "Airbus A380", "Boeing 737"), R.drawable.qantas_airways),
        Airline("Japan Airlines (JAL)", listOf("Boeing 787", "Airbus A350", "Boeing 777"), R.drawable.japan_airlines),
        Airline("Turkish Airlines", listOf("Boeing 737", "Boeing 777", "Airbus A330"), R.drawable.turkish_airlines),
        Airline("Air Canada", listOf("Airbus A320", "Boeing 777", "Boeing 787"), R.drawable.air_canada),
        Airline("Korean Air", listOf("Boeing 777", "Boeing 747", "Airbus A330"), R.drawable.korean_air),
        Airline("ANA (All Nippon Airways)", listOf("Boeing 787", "Airbus A380", "Boeing 777"), R.drawable.all_nippon_airways),
        Airline("Virgin Atlantic", listOf("Airbus A350", "Boeing 787", "Airbus A330"), R.drawable.virgin_atlantic),
        Airline("Etihad Airways", listOf("Boeing 787", "Airbus A380", "Boeing 777"), R.drawable.etihad_airways),
        Airline("Ryanair", listOf("Boeing 737"), R.drawable.ryanair)
    )
}