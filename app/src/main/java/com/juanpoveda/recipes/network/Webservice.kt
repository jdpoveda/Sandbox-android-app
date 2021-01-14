package com.juanpoveda.recipes.network

import com.juanpoveda.recipes.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// ****Retrofit s4: Add the interface with the required endpoints
interface Webservice {
    // ****Retrofit s4-1: There are 2 ways to define the endpoints. The normal way without coroutines and the suspend function with coroutines. You
    // can note the difference between both of them:
    @GET("search")
    suspend fun getRecipesByQuery(@Query("app_id") appId: String,
                          @Query("app_key") appKey: String,
                          @Query("q") q: String): SearchResponse
    @GET("search")
    fun getRecipesByQueryWithoutCoroutines(@Query("app_id") appId: String,
                                           @Query("app_key") appKey: String,
                                           @Query("q") q: String): Call<SearchResponse>
}