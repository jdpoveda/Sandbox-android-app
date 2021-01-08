package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.juanpoveda.recipes.data.RecipesRepository
import com.juanpoveda.recipes.model.SearchResponse

// ****MVVM s3: Generate the ViewModel (This one is automatically generated from the previous step).
class HomeViewModel
    /*(
    savedStateHandle: SavedStateHandle
) */
    : ViewModel() {
    //val q : String = savedStateHandle["q"] ?: //SavedStateHandle permite que ViewModel acceda al estado y los argumentos guardados del fragmento o actividad asociados.
    //throw IllegalArgumentException("missing query")

    // ****MVVM s4: Expose the data that will be consumed by the UI (Fragment or Activity) using LiveData. In this case
    // the response of the endpoint is exposed in recipeList
    val recipeList : LiveData<SearchResponse> = RecipesRepository.getRecipes("coffe")

}