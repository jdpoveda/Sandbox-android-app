package com.juanpoveda.recipes.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.juanpoveda.recipes.TestingObjects
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.repository.DefaultRecipesRepository
import com.juanpoveda.recipes.data.source.FakeDataSource
import com.juanpoveda.recipes.data.source.FakeTestRepository
import com.juanpoveda.recipes.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// ****ViewModelTest s5: Add this tag to the class to run with AndroidJUnit4. The AndroidJUnit4 test runner allows for AndroidX Test to run your test
// differently depending on whether they are instrumented or local tests.
@RunWith(AndroidJUnit4::class)
// ****ViewModelTest s14: Robolectric requires Java 9 to run tests on API 29 or above (Android Q). For now, set the sdk target to avoid error when running
@Config(sdk = [Build.VERSION_CODES.P])
// ****ViewModelTest s2: This class was generated from the previous step.
class RecipeListFromRepoViewModelTest {
    // ****ViewModelTest s6-1: Add the viewModel var to the class so it can be available for all the tests
    private lateinit var recipeListFromRepoViewModel: RecipeListFromRepoViewModel

    // ****ViewModelTest s10: Add this variable. InstantTaskExecutorRule is a JUnit Rule. When you use it with the @get:Rule annotation, it causes some
    // code in the InstantTaskExecutorRule class to be run before and after the tests. This rule runs all Architecture Components-related background jobs
    // in the same thread so that the test results happen synchronously, and in a repeatable order. When you write tests that include testing LiveData,
    // use this rule!
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    // ****TestRepositoryWithDoublesAndDependencyInjection s11: create a FakeTestRepository var to pass it to the viewModel
    private lateinit var fakeRepository: FakeTestRepository

    @Before
    fun initViewModel() {
        // ****TestRepositoryWithDoublesAndDependencyInjection s12: initialize the fakeRepository, add some items to it and then initialize the ViewModel.
        fakeRepository = FakeTestRepository()
        val r1 = TestingObjects.RECIPE_1
        val r2 = TestingObjects.RECIPE_2
        val r3 = TestingObjects.RECIPE_3
        fakeRepository.addRecipes(r1, r2, r3)

        // ****ViewModelTest s6-2: Initialize the viewModel now with androidx.test
        recipeListFromRepoViewModel = RecipeListFromRepoViewModel(ApplicationProvider.getApplicationContext(), fakeRepository)
    }

    // ****ViewModelTest s3: Add the test. As you can see, we need the context here so add the dependencies on the nest step.
    @Test
    fun onNetworkErrorShown_setsNetworkErrorToTrue() {

        // Given a RecipeListFromRepoViewModel
        // We already have the viewModel initialized: recipeListFromRepoViewModel

        // When calling to onNetworkErrorShown()
        // ****ViewModelTest s7: Now you can call the viewModel methods
        recipeListFromRepoViewModel.onNetworkErrorShown()

        // Then the isNetworkErrorShown value is set to true
        // ****ViewModelTest s12: Observe the LiveData value with the getOrAwaitValue() method.
        val value = recipeListFromRepoViewModel.isNetworkErrorShown.getOrAwaitValue()

        // ****ViewModelTest s13: Write the assertion
        assertThat(value, `is`(true))

    }
}