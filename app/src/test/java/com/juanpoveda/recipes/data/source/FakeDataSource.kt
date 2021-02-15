package com.juanpoveda.recipes.data.source

import androidx.lifecycle.LiveData
import com.juanpoveda.recipes.data.RecipesDataSource
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain

// ****TestRepositoryWithDoublesAndDependencyInjection s1: Create a FakeDataSource class in the /test directory and implement RecipesDataSource interface.
// We want to test DefaultRecipesRepository, but it has dependencies like Local/Remote DataSources. To test this, don't use the real networking or database code,
// but instead use a test double. A test double is a version of a class crafted specifically for testing. It is meant to replace the real version of a class in tests.
// Here are some types of test doubles:
// - Fake: A test double that has a "working" implementation of the class, but it's implemented in a way that makes it good for tests but unsuitable for production.
// - Mock: A test double that tracks which of its methods were called. It then passes or fails a test depending on whether it's methods were called correctly.
// - Stub: A test double that includes no logic and only returns what you program it to return. A StubTaskRepository could be programmed to return certain combinations of tasks from getTasks for example.
// - Dummy: A test double that is passed around but not used, such as if you just need to provide it as a parameter. If you had a NoOpTaskRepository, it would just implement the TaskRepository with no code in any of the methods.
// - Spy: A test double which also keeps tracks of some additional information; for example, if you made a SpyTaskRepository, it might keep track of the number of times the addTask method was called.
// For this test we'll use a FAKE. This class lets you test the code in DefaultTasksRepository without needing to rely on a real database or network.
// providing a "real-enough" implementation for tests.
class FakeDataSource(var recipesFakeList: MutableList<RecipeDomain>? = mutableListOf()): RecipesDataSource {
    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        TODO("Not yet implemented")
    }

    // ****TestRepositoryWithDoublesAndDependencyInjection s2: The main goal is to test the refreshRecipes() method in DefaultRecipesRepository. That method
    // invokes the methods getRecipes, deleteAllRecipes, saveRecipe in the DataSources, so we need to implement these in our FakeDataSource using the list
    // of recipes to simulate the database/network.
    override suspend fun getRecipes(query: String): Result<List<RecipeDomain>> {
        recipesFakeList?.let {
            val filteredList = it.filter { recipeDomain ->
                recipeDomain.label.contains(query, ignoreCase = true)
            }
            return Result.Success(ArrayList(filteredList))
        }
        return Result.Error(
            Exception("recipesFakeList not found")
        )
    }

    override suspend fun saveRecipe(recipe: RecipeDomain) {
        recipesFakeList?.add(recipe)
    }

    override suspend fun deleteAllRecipes() {
        recipesFakeList?.clear()
    }

    override suspend fun refreshRecipes(query: String) {
        TODO("Not yet implemented")
    }

    override fun observeRecipe(recipeUrl: String): LiveData<Result<RecipeDomain>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipe(recipeUrl: String): Result<RecipeDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshRecipe(recipeUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipe(recipeUrl: String): Int {
        TODO("Not yet implemented")
    }
}