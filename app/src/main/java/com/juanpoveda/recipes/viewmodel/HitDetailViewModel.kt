package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpoveda.recipes.data.network.HitDTO
import com.juanpoveda.recipes.data.network.RecipeDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HitDetailViewModel : ViewModel() {
    private val _selectedHit = MutableLiveData<HitDTO>()
    val selectedHit: LiveData<HitDTO>
        get() = _selectedHit
    // ****NavigationWithLiveData s1: Add the variables to store the data that will be passed to the next Fragment in the ViewModel.
    private val _navigateToStepsFragment = MutableLiveData<RecipeDTO>()
    val navigateToStepsFragment: LiveData<RecipeDTO>
        get() = _navigateToStepsFragment

    init {
        // ****NavigationWithLiveData s4: Initialize the navigation variable to null
        doneNavigatingToStepsFragment()
    }

    fun setNewHit(hit: HitDTO) {
        _selectedHit.value = hit
    }

    // ****NavigationWithLiveData s2: Add a method to clear the navigation variable
    fun doneNavigatingToStepsFragment() {
        _navigateToStepsFragment.value = null
    }

    // ****NavigationWithLiveData s3: Add a method to set the data that you want to pass
    fun launchNavigationToStepsFragment(recipe: RecipeDTO) {
        // ****NavigationWithLiveData s7: Here you can call any required things (database calls, network calls, etc) and set the navigation variable when
        // completed. For this example we'll simulate this with a random delay. When the value is set to _navigateToStepsFragment, the Fragment will observe the
        // change and launch the navigation to the other Fragment.
        viewModelScope.launch {
            delay((1000L..4000L).random())
            _navigateToStepsFragment.value = recipe
        }
    }
}