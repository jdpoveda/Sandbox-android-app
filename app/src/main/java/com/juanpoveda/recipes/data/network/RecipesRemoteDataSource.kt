package com.juanpoveda.recipes.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.data.RecipesDataSource
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

// ****ImproveRepositoryWithDataSources s7: Now, create the remote data source extending RecipesDataSource also. Here we only need the ioDispatcher and the
// idea is to override all the methods and code the logic to perform the operations in the API.
class RecipesRemoteDataSource internal constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): RecipesDataSource {

    // ****ImproveRepositoryWithDataSources s8: Here, we need a MutableLiveData object to store the observableRecipes
    private val observableRecipes = MutableLiveData<Result<List<RecipeDomain>>>()

    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        return observableRecipes
    }

    override suspend fun getRecipes(query: String): Result<List<RecipeDomain>> = withContext(ioDispatcher) {
        // ****ImproveRepositoryWithDataSources s9: The API can be called this way inside a coroutine, and the results must be wrapped in a Result<> object as well
        return@withContext try {
            Result.Success(RecipesApi.retrofitService.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, query).asDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshRecipes(query: String) {
        observableRecipes.value = getRecipes(query)
    }

    override fun observeRecipe(recipeUrl: String): LiveData<Result<RecipeDomain>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipe(recipeUrl: String): Result<RecipeDomain> = withContext(ioDispatcher) {
        return@withContext try {
            val urlEncoded = java.net.URLEncoder.encode(recipeUrl, "utf-8")
            Result.Success(RecipesApi.retrofitService.getRecipeByUrl(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, urlEncoded)[0].asDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun refreshRecipe(recipeId: String) {
        // Not needed
    }

    override suspend fun saveRecipe(recipe: RecipeDomain) {
        // Not needed (could call a POST endpoint in the future)
    }

    override suspend fun deleteAllRecipes() {
        // Not needed (could call a DELETE endpoint in the future)
    }

    override suspend fun deleteRecipe(recipeId: String): Int {
        // Not needed (could call a DELETE endpoint in the future)
        return -1
    }
}