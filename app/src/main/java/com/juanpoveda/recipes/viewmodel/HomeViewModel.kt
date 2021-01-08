package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.juanpoveda.recipes.data.RecipesRepository
import com.juanpoveda.recipes.model.SearchResponse

class HomeViewModel
    /*(
    savedStateHandle: SavedStateHandle
) */
    : ViewModel() {
    //val q : String = savedStateHandle["q"] ?: //SavedStateHandle permite que ViewModel acceda al estado y los argumentos guardados del fragmento o actividad asociados.
    //throw IllegalArgumentException("missing query")
    val recipeList : LiveData<SearchResponse> = RecipesRepository.getRecipes("coffe")



}