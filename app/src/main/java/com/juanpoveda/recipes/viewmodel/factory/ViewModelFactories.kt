package com.juanpoveda.recipes.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanpoveda.recipes.data.database.RecipesDatabaseDAO
import com.juanpoveda.recipes.data.repository.RecipesRepository
import com.juanpoveda.recipes.viewmodel.HomeViewModel
import com.juanpoveda.recipes.viewmodel.RecipeListFromRepoViewModel

// ****ViewModel s5: (OPTIONAL) This step is needed only when the ViewModel needs to receive some arguments from the View. Create the Factory class with
// all the params you need following this example:
class HomeViewModelFactory(
    private val dataSource: RecipesDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// ****ImproveRepositoryWithDataSources s13: Be sure to include the RecipesRepository param in the ViewModel factory
class RecipeListFromRepoViewModelFactory(
    private val application: Application,
    private val newRepo: RecipesRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListFromRepoViewModel::class.java)) {
            return RecipeListFromRepoViewModel(application, newRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}