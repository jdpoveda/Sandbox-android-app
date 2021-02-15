package com.juanpoveda.recipes.data.source

import com.juanpoveda.recipes.TestingObjects
import com.juanpoveda.recipes.data.Result
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.repository.DefaultRecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

// ****TestRepositoryWithDoublesAndDependencyInjection s3: Create a Test class for DefaultRecipesRepository. Add the variables that will represent the data
// from the database and the network.
class DefaultRecipesRepositoryTest {
    private val rec1 = TestingObjects.RECIPE_1
    private val rec2 = TestingObjects.RECIPE_2
    private val rec3 = TestingObjects.RECIPE_3

    // ****TestRepositoryWithDoublesAndDependencyInjection s4: Create the lists to simulate the local and remote info. Add the variables to store the FakeDataSources
    // for local and remote and also a variable to store the class to test (DefaultRecipesRepository)
    private val localRecipes = listOf(rec2).sortedBy { it.uri }
    private val remoteRecipes = listOf(rec1, rec3).sortedBy { it.uri }
    private val newRecipes = listOf(rec1).sortedBy { it.uri }

    private lateinit var recipesRemoteDataSource: FakeDataSource
    private lateinit var recipesLocalDataSource: FakeDataSource
    // This is the class that we want to test
    private lateinit var recipesRepository: DefaultRecipesRepository

    // ****TestRepositoryWithDoublesAndDependencyInjection s5: Add a method with @Before annotation to create a new Repository passing the FakeDataSources
    @Before
    fun createRepository() {
        recipesRemoteDataSource = FakeDataSource(remoteRecipes.toMutableList())
        recipesLocalDataSource = FakeDataSource(localRecipes.toMutableList())
        recipesRepository = DefaultRecipesRepository(
            // TODO Dispatchers.Unconfined should be replaced with Dispatchers.Main
            //  this requires understanding more about coroutines + testing
            //  so we will keep this as Unconfined for now.
            recipesLocalDataSource, recipesRemoteDataSource, Dispatchers.Unconfined
        )
    }

    // ****TestRepositoryWithDoublesAndDependencyInjection s7: Add the test for the first method in the recipesRepository, we're going to test getRecipes()
    // that deletes all the existing recipes in the local database and inserts the recipes retrieved by tne network into the local database. Note that
    // we need to run the code inside a coroutine so we use runBlockingTest that is a simulated coroutine for testing purposes.
    @Test
    fun refreshRepository_deletesAllExistingLocalRecipesAndInsertTheRecipesFromNetwork() = runBlockingTest {
        val recipes = recipesRepository.getRecipes(true,"coffe") as Result.Success
        assertThat(recipes.data, IsEqual(remoteRecipes))
    }
}