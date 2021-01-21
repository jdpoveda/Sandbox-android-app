package com.juanpoveda.recipes.data.repository

import androidx.lifecycle.LiveData
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain

interface RecipesRepository {

    suspend fun refreshRecipes(query: String)

    fun observeRecipes(): LiveData<Result<List<RecipeDomain>>>

}