package com.juanpoveda.recipes

import android.app.Application
import timber.log.Timber

class RecipesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // ****Timber s2: Initialize Timber in the Application class
        Timber.plant(Timber.DebugTree())
    }
}