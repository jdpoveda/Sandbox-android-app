package com.juanpoveda.recipes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.juanpoveda.recipes.data.database.RecipesDatabase
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.repository.DefaultRecipesRepositoryWithoutDataSources
import com.juanpoveda.recipes.data.repository.RecipesRepository
import kotlinx.coroutines.launch
import java.io.IOException
import com.juanpoveda.recipes.data.Result

// ****ImproveRepositoryWithDataSources s12: To be able to use the new repository in the ViewModel, add it as a param in the constructor. Note that we'll
// preserve the application param as this ViewModel will contain both the old Repository and the new one.
class RecipeListFromRepoViewModel(application: Application, private val newRepository: RecipesRepository) : AndroidViewModel(application) {

    private val _recipeDomainList = MutableLiveData<List<RecipeDomain>>()
    val recipeDomainList: LiveData<List<RecipeDomain>>
        get() = _recipeDomainList
    // ****Repository s8: To use the Repository from the ViewModel, add a variable with the instance of the DB
    private val recipesRepository = DefaultRecipesRepositoryWithoutDataSources(RecipesDatabase.getInstance(application))
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

    // ****ImproveRepositoryWithDataSources s15: Now we can get the recipes list bu calling recipesRepository.observeRecipes(). As they are wrapped in a
    // Result object, we need to filter the result, that way we can return the plain LiveData object
    private val _recipeListNewRepo: LiveData<List<RecipeDomain>> = newRepository.observeRecipes().switchMap { filterRecipes(it) }
    val recipeListNewRepo: LiveData<List<RecipeDomain>> = _recipeListNewRepo

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

    // ****ImproveRepositoryWithDataSources s16: Using the new Repo, update the recipes by calling refreshRecipes
    fun searchRecipesFromNewRepo(query: String) {
        viewModelScope.launch {
            try {
                newRepository.refreshRecipes(query)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(recipes.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    private fun filterRecipes(recipesResult: Result<List<RecipeDomain>>): LiveData<List<RecipeDomain>> {
        val result = MutableLiveData<List<RecipeDomain>>()

        if (recipesResult is Result.Success) {
            //isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = recipesResult.data
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            }
        } else {
            result.value = emptyList()
            _eventNetworkError.value = true
            //showSnackbarMessage(R.string.loading_tasks_error)
            //isDataLoadingError.value = true
        }

        return result
    }


}