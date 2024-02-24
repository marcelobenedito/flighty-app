package com.github.marcelobenedito.flighty

import android.app.Application
import com.github.marcelobenedito.flighty.data.AppContainer
import com.github.marcelobenedito.flighty.data.DefaultAppContainer

class FlightyApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer(this)
    }
}