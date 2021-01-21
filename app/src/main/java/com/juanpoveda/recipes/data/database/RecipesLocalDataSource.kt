package com.juanpoveda.recipes.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.juanpoveda.recipes.data.RecipesDataSource
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.domain.asDatabaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

// ****ImproveRepositoryWithDataSources s3: Create the RecipesLocalDataSource and extend the previously created RecipesDataSource. Implement all the methods and
// start coding the logic to make all these operations with the database. Here we need to receive the database and the ioDispatcher as we need to make some
// ops inside coroutines.
class RecipesLocalDataSource internal constructor(
    private val database: RecipesDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): RecipesDataSource {

    // ****ImproveRepositoryWithDataSources s4: There are 2 types of methods here: observe and get, the difference is that observe methods return a LiveData.
    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        return database.recipesDatabaseDAO.getAllDatabaseRecipes().map {
            // ****ImproveRepositoryWithDataSources s5: We must return our responses wrapped into the previously created class Result<>, it can be Success or Error.
            Result.Success(it.asDomainModel())
        }
    }

    override fun observeRecipe(recipeUrl: String): LiveData<Result<RecipeDomain>> {
        return database.recipesDatabaseDAO.observeDatabaseRecipeByUrl(recipeUrl).map {
            Result.Success(it.asDomainModel())
        }
    }


    override suspend fun getRecipes(query: String): Result<List<RecipeDomain>> = withContext(ioDispatcher) {
        // ****ImproveRepositoryWithDataSources s6: The result can be Success or Error.
        return@withContext try {
            Result.Success(database.recipesDatabaseDAO.getDatabaseRecipesByQuery(query).asDomainModel())
        } catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getRecipe(recipeUrl: String): Result<RecipeDomain> = withContext(ioDispatcher) {
        try {
            val recipe = database.recipesDatabaseDAO.getDatabaseRecipeByUrl(recipeUrl)?.asDomainModel()
            if (recipe != null) {
                return@withContext Result.Success(recipe)
            } else {
                return@withContext Result.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun refreshRecipes(query: String) {
        // Not needed in local
    }

    override suspend fun refreshRecipe(recipeUrl: String) {
        // Not needed in local
    }

    override suspend fun saveRecipe(recipe: RecipeDomain) = withContext(ioDispatcher) {
        database.recipesDatabaseDAO.insert(recipe.asDatabaseModel())
    }

    override suspend fun deleteAllRecipes() = withContext(ioDispatcher) {
        database.recipesDatabaseDAO.deleteAllRecipes()
    }

    override suspend fun deleteRecipe(recipeUrl: String) = withContext(ioDispatcher) {
        database.recipesDatabaseDAO.deleteRecipeByUrl(recipeUrl)
    }

}