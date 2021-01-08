package com.juanpoveda.recipes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesRepository() {

    companion object {
        val request: Webservice = ServiceBuilder.buildService(Webservice::class.java) //****Retrofit s5: call buildService passing the corresponding interface

        fun getRecipes(queryParam: String) : LiveData<SearchResponse> {
            val data = MutableLiveData<SearchResponse>()
            //****Retrofit s6: call the endpoint and handle success and failure scenarios
            val call = request.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, queryParam)

            call.enqueue(object: Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    data.value = response.body()
                }
            })

            return data
        }
    }

}