package com.juanpoveda.recipes

import android.app.Application
import android.os.Build
import androidx.work.*
import com.juanpoveda.recipes.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RecipesApplication: Application() {

    // ****WorkManager s9: Add the applicationScope using Coroutines for the Application class
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        // ****Timber s2: Initialize Timber in the Application class
        Timber.plant(Timber.DebugTree())
        // ****WorkManager s11: Call the Worker
        delayedInit()
    }

    // ****WorkManager s6: We're going to setup and call the worker in the Application class for this example.
    private fun setupRecurringWork() {

        // ****WorkManager s12 (optional): Add a constraints variable. This can be used to setup the recurring work to configure execution only if the device has high battery,
        // is in iddle state, is connected to a wifi network, etc.
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // To use only if wifi network is enabled
            .setRequiresBatteryNotLow(true) // This will run only if teh device has a good battery level
            //.setRequiresCharging(true) // This will require the device to be in charging state
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true) // This constraint runs the work request only when the user isn't actively using the device. This feature is only available in Android 6.0 (Marshmallow) and higher, so add a condition for SDK version M and higher.
                }
            }
            .build()

        // ****WorkManager s8: Create a PeriodicWorkRequestBuilder with the previously created Worker and Schedule the work using the
        // enqueueUniquePeriodicWork() method. Here, the policy can be configured to keep or replace the running worker if it has the same name.
        // If pending (uncompleted) work exists with the same name, the ExistingPeriodicWorkPolicy.KEEP parameter makes the WorkManager keep
        // the previous periodic work and discard the new work request.
        // ****WorkManager s13 (optional): pass the constraints variable to the PeriodicWorkRequestBuilder
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        Timber.d("Periodic Work request for sync is scheduled")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    // ****WorkManager s10: Call the setupRecurringWork method in the applicationScope
    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }


}