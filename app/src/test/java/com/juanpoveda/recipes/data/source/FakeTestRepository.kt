package com.juanpoveda.recipes.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.repository.RecipesRepository
import kotlinx.coroutines.runBlocking

// ****TestRepositoryWithDoublesAndDependencyInjection s8: Now we're going to create a Fake repository. This will be needed in order to test the ViewModels, because
// they require a RecipesRepository param in the constructor. Create the class and extend the Repository interface
class FakeTestRepository: RecipesRepository {

    // ****TestRepositoryWithDoublesAndDependencyInjection s9: This fake repo doesn't need FakeDataSources, it should be able to return realistic fake outputs, so
    // create the following variables to simulate the data and the LiveData.
    var recipesServiceData: LinkedHashMap<String, RecipeDomain> = LinkedHashMap()
    private val observableRecipes = MutableLiveData<Result<List<RecipeDomain>>>()

    // ****TestRepositoryWithDoublesAndDependencyInjection s10: Implement the required methods to return/save the fake data. Add an aux method to addRecipes
    override suspend fun refreshRecipes(query: String) {
        observableRecipes.value = getRecipes(true, "")
    }

    override suspend fun getRecipes(forceUpdate: Boolean, query: String): Result<List<RecipeDomain>> {
        return Result.Success(recipesServiceData.values.toList())
    }

    override fun observeRecipes(): LiveData<Result<List<RecipeDomain>>> {
        runBlocking { refreshRecipes("") }
        return observableRecipes
    }

    fun addRecipes(vararg recipes: RecipeDomain) {
        for (rec in recipes) {
            recipesServiceData[rec.uri] = rec
        }
        runBlocking { refreshRecipes("") }
    }
}