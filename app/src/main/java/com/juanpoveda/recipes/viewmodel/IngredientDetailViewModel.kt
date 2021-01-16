package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.juanpoveda.recipes.network.Ingredient

class IngredientDetailViewModel : ViewModel() {

    private val _ingredientList = MutableLiveData<List<Ingredient>>()
    val ingredientList: LiveData<List<Ingredient>>
        get() = _ingredientList
    // ****Transformations s1: Add the variable to expose in the viewModel. An already-defined LiveData variable must be passed to Transformation.map()
    val transformedIngredientList = Transformations.map(ingredientList) {
        // ****Transformations s2: Here the LiveData variable is accessible, and it can be transformed depending on the needs. In this example,
        // the customText field is set for each ingredient to include a "->" before the description of each ingredient.
        it.map{ingredient ->
            var ing = ingredient
            ing.customText = "-> " + ingredient.text
            ing }
    }

    init {
        setIngredientList(emptyList())
    }

    fun setIngredientList(ingredients: List<Ingredient>) {
        _ingredientList.value = ingredients
    }
}