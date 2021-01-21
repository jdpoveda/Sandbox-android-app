package com.juanpoveda.recipes

import android.content.Context
import com.juanpoveda.recipes.data.RecipesDataSource
import com.juanpoveda.recipes.data.database.RecipesDatabase
import com.juanpoveda.recipes.data.database.RecipesLocalDataSource
import com.juanpoveda.recipes.data.network.RecipesRemoteDataSource
import com.juanpoveda.recipes.data.repository.DefaultRecipesRepository
import com.juanpoveda.recipes.data.repository.RecipesRepository

// ****ImproveRepositoryWithDataSources s10: Create an Object that will have the method to provide a RecipesRepository so it can be used later.
object ServiceLocator {

    @Volatile
    var recipesRepository: RecipesRepository? = null
    private var database: RecipesDatabase? = null

    fun provideRecipesRepository(context: Context): RecipesRepository {
        synchronized(this) {
            return recipesRepository ?: createRecipesRepository(context)
        }
    }

    private fun createRecipesRepository(context: Context): RecipesRepository {
        val newRepo = DefaultRecipesRepository(createRecipesLocalDataSource(context), createRecipesRemoteDataSource())
        recipesRepository = newRepo
        return newRepo
    }

    private fun createRecipesRemoteDataSource(): RecipesDataSource {
        return RecipesRemoteDataSource()
    }

    private fun createRecipesLocalDataSource(context: Context): RecipesDataSource {
        val database = database ?: RecipesDatabase.getInstance(context)
        return RecipesLocalDataSource(database)
    }

}