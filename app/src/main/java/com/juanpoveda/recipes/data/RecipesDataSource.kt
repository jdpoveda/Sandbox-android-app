package com.juanpoveda.recipes.data

import androidx.lifecycle.LiveData
import com.juanpoveda.recipes.data.domain.RecipeDomain

// ****ImproveRepositoryWithDataSources s1: At this point, we already have domain models, DAOs, DTOs but it's better to unify the local and remote operations, and
// this can be achieved by using DataSources. First, create an interface that will contain all the methods needed in both data sources (local and remote):
interface RecipesDataSource {

    fun observeRecipes(): LiveData<Result<List<RecipeDomain>>>

    suspend fun getRecipes(query: String): Result<List<RecipeDomain>>

    suspend fun refreshRecipes(query: String)

    fun observeRecipe(recipeUrl: String): LiveData<Result<RecipeDomain>>

    suspend fun getRecipe(recipeUrl: String): Result<RecipeDomain>

    suspend fun refreshRecipe(recipeUrl: String)

    suspend fun saveRecipe(recipe: RecipeDomain)

    suspend fun deleteAllRecipes()

    suspend fun deleteRecipe(recipeUrl: String): Int

}