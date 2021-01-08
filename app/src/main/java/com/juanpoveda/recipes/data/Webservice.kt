package com.juanpoveda.recipes.data

import com.juanpoveda.recipes.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//****Retrofit s3: Add the interface with the required endpoints
interface Webservice { 
    @GET("search")
    fun getRecipesByQuery(@Query("app_id") appId: String,
                          @Query("app_key") appKey: String,
                          @Query("q") q: String): Call<SearchResponse>
}