package com.juanpoveda.recipes.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ****Retrofit s5: Add the service builder with the baseUrl and the GSON converter (option 2/2)

private const val BASE_URL = "https://api.edamam.com/"


/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RecipesApi {
    val retrofitService : Webservice by lazy { retrofit.create(Webservice::class.java) }
}