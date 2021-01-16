package com.juanpoveda.recipes.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.repository.RecipesRepository
import retrofit2.HttpException
import timber.log.Timber

// ****WorkManager s2: Create the Worker class. It must receive the appContext and the WorkerParameters and it must extend CoroutineWorker.
// This class is where you define the actual work (the task) to run in the background. You extend this class and override the doWork() method.
// The doWork() method is where you put code to be performed in the background, such as syncing data with the server or processing images.
// You implement the Worker in this task.
class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    // ****WorkManager s7: Add a unique name to the Worker. Only one PeriodicWorkRequest of a particular name can be active at a time. There are some
    // policies that can be configured to handle a work if there is a work with the same name running (it can be replaced with the new one for example)
    companion object {
        const val WORK_NAME = "com.juanpoveda.recipes.work.RefreshDataWorker"
    }

    // ****WorkManager s3: Override the doWork() method. The doWork() method inside the Worker class is called on a background thread.
    // The method performs work synchronously, and should return a ListenableWorker.Result object. The Android system gives a Worker a maximum of
    // 10 minutes to finish its execution and return a ListenableWorker.Result object. After this time has expired, the system forcefully stops the Worker.
    override suspend fun doWork(): Result {

        // ****WorkManager s4: We can use the database and repository in our Worker. Here, we'll make a network call to get some Recipes in the background
        // and insert them into the DB.
        val database = RecipesDatabase.getInstance(applicationContext)
        val repository = RecipesRepository(database)

        try {
            repository.refreshRecipes("vodka")
            Timber.d("Work request for sync is run")
        } catch (e: HttpException) {
            Timber.d("Work request failed. launching Retry")
            return Result.retry()
        }

        // ****WorkManager s5: The doWork method must return one of the following options, note that we returned Result.retry() in the HttpException catch block.
        // Result.success()—work completed successfully.
        // Result.failure()—work completed with a permanent failure.
        // Result.retry()—work encountered a transient failure and should be retried.
        return Result.success()
    }
}