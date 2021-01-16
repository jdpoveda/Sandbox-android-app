package com.juanpoveda.recipes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.database.asDomainModel
import com.juanpoveda.recipes.domain.RecipeDomain
import com.juanpoveda.recipes.network.Hit
import com.juanpoveda.recipes.network.SearchResponse
import com.juanpoveda.recipes.network.RecipesApi
import com.juanpoveda.recipes.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

// ****Repository s5: Create the Repository class. It must be the unique source of data for the app. The endpoints are called
// here and if no network connection is available, the methods should return the cached data or the local database data. Pass in a
// RecipesDatabase object as the class's constructor parameter to access the Dao methods.
class RecipesRepository(private val database: RecipesDatabase) {

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
    suspend fun refreshRecipes(query: String) {
        //_searchParam.value = query
        withContext(Dispatchers.IO) {
            Timber.d("refresh recipes called");
            val listResult = RecipesApi.retrofitService.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, query)
            database.recipesDatabaseDAO.insertAll(listResult.asDatabaseModel())
           // _recipes.value = database.recipesDatabaseDAO.getDatabaseRecipesByQuery("%$query%").asDomainModel()
        }

    }

    companion object {

        fun getRecipes(queryParam: String) : LiveData<List<Hit>?> {
            val data = MutableLiveData<List<Hit>?>()
            // ****Retrofit s6: call the desired request
            val call = RecipesApi.retrofitService.getRecipesByQueryWithoutCoroutines(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, queryParam)

            call.enqueue(object: Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    data.value = response.body()?.hits
                }
            })

            return data
        }
    }

}