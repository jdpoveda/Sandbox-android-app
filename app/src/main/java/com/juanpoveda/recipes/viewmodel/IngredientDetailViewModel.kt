package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juanpoveda.recipes.model.Ingredient

class IngredientDetailViewModel : ViewModel() {

    private val _ingredientList = MutableLiveData<List<Ingredient>?>()
    val ingredientList: LiveData<List<Ingredient>?>
        get() = _ingredientList
    // TODO: Implement the ViewModel
}