package com.juanpoveda.recipes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.database.RecipeReview
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.database.RecipesDatabaseDAO
import com.juanpoveda.recipes.domain.RecipeDomain
import com.juanpoveda.recipes.network.Hit
import com.juanpoveda.recipes.network.RecipesApi
import com.juanpoveda.recipes.repository.RecipesRepository
import kotlinx.coroutines.launch
import java.io.IOException

class RecipeListFromRepoViewModel(application: Application) : AndroidViewModel(application) {

    private val _recipeDomainList = MutableLiveData<List<RecipeDomain>>()
    val recipeDomainList: LiveData<List<RecipeDomain>>
        get() = _recipeDomainList
    // ****Repository s8: To use the Repository from the ViewModel, add a variable with the instance of the DB
    private val recipesRepository = RecipesRepository(RecipesDatabase.getInstance(application))
    // ****Repository s9: Add a variable to get the list from the Repo, note that recipesRepository.recipes is LiveData so it will be updated automatically after
    // a DB update
    val recipes = recipesRepository.allRecipes
    // ****Repository s10: Add a the variables to know if there is some network error or so
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
        _recipeDomainList.value = emptyList()
    }

    // ****Repository s11: Without repository, we normally do a network call here. Now we'll call the repo method to refresh the recipes that will trigger the network
    // call and then insert the data into the DB causing an instant update of the previously defined val recipes = recipesRepository.recipes
    fun searchRecipesFromRepo(query: String) {
        viewModelScope.launch {
            try {
                recipesRepository.refreshRecipes(query)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(recipes.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    // ****ViewModelTest s1: We will start testing this simple method. The network and database calls add more complexity and will be reviewed later. First, create
    // a test class for this viewModel. Right click in the class name -> generate -> test. Select JUnit 4 and for destination directory select test/
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

}