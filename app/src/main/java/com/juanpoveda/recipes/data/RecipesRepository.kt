package com.juanpoveda.recipes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.model.Hit
import com.juanpoveda.recipes.model.SearchResponse
import com.juanpoveda.recipes.network.RecipesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ****Repository s1: Create the Repository class. It must be the unique source of data for the app. The endpoints are called
// here and if no network connection is available, the methods should return the cached data or the local database data.
class RecipesRepository() {

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