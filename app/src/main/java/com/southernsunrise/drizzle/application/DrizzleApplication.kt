package com.southernsunrise.drizzle.application

import android.app.Application

class DrizzleApplication : Application() {
    // Creating an instance of AppContainer which plays as Centralized dependency management class
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Initialize AppContainer with application context
        appContainer = AppContainer(applicationContext)
    }
}