package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.*
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.data.RecipesRepository
import com.juanpoveda.recipes.model.Hit
import com.juanpoveda.recipes.model.SearchResponse
import com.juanpoveda.recipes.network.RecipesApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ****ViewModel s2: Generate the ViewModel (This one is automatically generated from the previous step).
class HomeViewModel
/*(
savedStateHandle: SavedStateHandle
) */
    : ViewModel() {

    // ****ViewModel s3: Expose the data that will be consumed by the UI (Fragment or Activity) using LiveData. In this case
    // we want to expose the recipe list so the View (HomeFragment) can render the list. It's important to expose LiveData and keep a MutableLiveData
    // private, because the only one that must make changes to this variable is the ViewModel
    private val _hitList = MutableLiveData<List<Hit>?>()
    val hitList: LiveData<List<Hit>?>
        get() = _hitList

    init {
        searchHits("")
    }

    // ****ViewModel s4: Create the methods that will be consumed by the View, for example this one will receive the query param from the view and
    // update the recipe list based on the query.
    fun searchHits(query: String) {
        viewModelScope.launch {
            val call = RecipesApi.retrofitService.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, query)
            call.enqueue(object: Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    _hitList.value = response.body()?.hits
                }
            })
        }
    }

    //val q : String = savedStateHandle["q"] ?: //SavedStateHandle permite que ViewModel acceda al estado y los argumentos guardados del fragmento o actividad asociados.
    //throw IllegalArgumentException("missing query")


    //val recipeList : LiveData<SearchResponse> = RecipesRepository.getRecipes("coffe")

}