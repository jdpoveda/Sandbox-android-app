package com.juanpoveda.recipes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.data.RecipesDataSource
import com.juanpoveda.recipes.data.database.RecipesLocalDataSource
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.network.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import com.juanpoveda.recipes.data.Result

class DefaultRecipesRepository(private val recipesLocalDataSource: RecipesDataSource,
                               private val recipesRemoteDataSource: RecipesDataSource,
                               private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): RecipesRepository {

    private val _recipes = MutableLiveData<List<RecipeDomain>>()
    val recipes: LiveData<List<RecipeDomain>>
        get() = _recipes


    override suspend fun refreshRecipes(query: String) = withContext(ioDispatcher) {
        val remoteRecipes = recipesRemoteDataSource.getRecipes(query)

        if (remoteRecipes is Result.Success) {
            // Real apps might want to do a proper sync.
            recipesLocalDataSource.deleteAllRecipes()
            remoteRecipes.data.forEach { recipe ->
                recipesLocalDataSource.saveRecipe(recipe)
            }
        } else if (remoteRecipes is Result.Error) {
            throw remoteRecipes.exception
        }
    }

    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        return recipesLocalDataSource.observeRecipes()
    }

    private suspend fun updateRecipesFromRemoteDataSource(query: String) {

    }

}