package com.juanpoveda.recipes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpoveda.recipes.data.RecipesDataSource
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRecipesRepository(val recipesLocalDataSource: RecipesDataSource,
                               val recipesRemoteDataSource: RecipesDataSource,
                               private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): RecipesRepository {

    private val _recipes = MutableLiveData<List<RecipeDomain>>()
    val recipes: LiveData<List<RecipeDomain>>
        get() = _recipes


    override suspend fun refreshRecipes(query: String) = withContext(ioDispatcher) {
        updateRecipesFromRemote(query)
    }

    override suspend fun getRecipes(forceUpdate: Boolean, query: String): Result<List<RecipeDomain>> {
        if (forceUpdate) {
            try {
                updateRecipesFromRemote(query)
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return recipesLocalDataSource.getRecipes(query)
    }

    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        return recipesLocalDataSource.observeRecipes()
    }

    private suspend fun updateRecipesFromRemote(query: String) {
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

}