package com.juanpoveda.recipes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.database.RecipesDatabase
import com.juanpoveda.recipes.data.database.asDomainModel
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.network.HitDTO
import com.juanpoveda.recipes.data.network.SearchResponseDTO
import com.juanpoveda.recipes.data.network.RecipesApi
import com.juanpoveda.recipes.data.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

// ****Repository s5: Create the Repository class. It must be the unique source of data for the app. The endpoints are called
// here and if no network connection is available, the methods should return the cached data or the local database data. Pass in a
// RecipesDatabase object as the class's constructor parameter to access the Dao methods.
class DefaultRecipesRepositoryWithoutDataSources(private val database: RecipesDatabase): RecipesRepository {

    // ****Repository s7: Add a LiveData variable to hold the list of recipes returned by the database. This LiveData object is automatically
    // updated when the database is updated. The attached fragment, or the activity, is refreshed with new values.
    // Note that we need to use a Transformation here because we want to transform the database objects to domain objects.
    val allRecipes = Transformations.map(database.recipesDatabaseDAO.getAllDatabaseRecipes()) {
        it.asDomainModel()
    }
    private val _recipes = MutableLiveData<List<RecipeDomain>>()
    val recipes: LiveData<List<RecipeDomain>>
        get() = _recipes
    //private val _searchParam = MutableLiveData<String>()
    //val searchParam: LiveData<String>
    //    get() = _searchParam



    // ****Repository s6: Add a method to refresh the recipe list, it must be suspend because it will be called from a coroutine as it performs DB operations.
    // In this method we'll get the recipe list by calling the endpoint and then we'll insert all the retrieved items to the DB
    override suspend fun refreshRecipes(query: String) {
        //_searchParam.value = query
        withContext(Dispatchers.IO) {
            Timber.d("refresh recipes called");
            val listResult = RecipesApi.retrofitService.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, query)
            database.recipesDatabaseDAO.insertAll(listResult.asDatabaseModel())
           // _recipes.value = database.recipesDatabaseDAO.getDatabaseRecipesByQuery("%$query%").asDomainModel()
        }

    }

    override suspend fun getRecipes(forceUpdate: Boolean, query: String): Result<List<RecipeDomain>> {
        TODO("Not yet implemented")
    }

    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        TODO("Not yet implemented")
    }

}