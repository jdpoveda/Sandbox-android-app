package com.juanpoveda.recipes.viewmodel

import android.app.Application
import android.app.NotificationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juanpoveda.recipes.BuildConfig
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.data.database.RecipeReview
import com.juanpoveda.recipes.data.database.RecipesDatabaseDAO
import com.juanpoveda.recipes.data.network.HitDTO
import com.juanpoveda.recipes.data.network.RecipesApi
import com.juanpoveda.recipes.util.cancelNotifications
import com.juanpoveda.recipes.util.sendNotification
import kotlinx.coroutines.launch

// ****RecyclerViewErrorHandling s1: Add an Enum with the possible API states
enum class RecipesApiStatus { LOADING, ERROR, DONE }

// ****ViewModel s2: Generate the ViewModel (This one is automatically generated from the previous step). Here, the class can extend from ViewModel() or
// can extend AndroidViewModel(application). The difference is that the AndroidViewModel includes the context.
// ****Room s13: The viewModel must receive the DAO as an argument.
class HomeViewModel(val database: RecipesDatabaseDAO,
                    private val app: Application) : AndroidViewModel(app) {

    // ****ViewModel s3: Expose the data that will be consumed by the UI (Fragment or Activity) using LiveData. In this case
    // we want to expose the recipe list so the View (HomeFragment) can render the list. It's important to expose LiveData and keep a MutableLiveData
    // private, because the only one that must make changes to this variable is the ViewModel
    private val _hitList = MutableLiveData<List<HitDTO>?>()
    val hitList: LiveData<List<HitDTO>?>
        get() = _hitList
    private val _lastReviewedRecipe = MutableLiveData<RecipeReview>()
    val lastReviewedRecipe: LiveData<RecipeReview>
        get() = _lastReviewedRecipe
    // ****Room s18: If the DAO returns a LiveData object, then it can be called directly and Room will update it automatically.
    val reviewedRecipes = database.getAllRecipeReviews()
    // ****RecyclerViewErrorHandling s2: Add the variables, so the ViewModel can know the current state of the API
    private val _status = MutableLiveData<RecipesApiStatus>()
    val status: LiveData<RecipesApiStatus>
        get() = _status

    init {
        _hitList.value = emptyList()
        _status.value = RecipesApiStatus.DONE
        initializeLastRecipeReview()
    }

    // ****ViewModel s4: Create the methods that will be consumed by the View, for example this one will receive the query param from the view and
    // update the recipe list based on the query.
    fun searchHits(query: String) {
        // ****Retrofit s6: Call the desired request
        viewModelScope.launch {

            // ****RecyclerViewErrorHandling s3: Set the status to LOADING because we're going to make an API call
            _status.value = RecipesApiStatus.LOADING
            _hitList.value = emptyList()

            // ****Retrofit s6-1: If you want to call without coroutines then this is the way:
            /*val call = RecipesApi.retrofitService.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, query)
            call.enqueue(object: Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    _hitList.value = response.body()?.hits
                }
            }) */

            // ****Retrofit s6-2: To make the calls with coroutines, follow this pattern (it's easier to read and more efficient)
            try {
                val listResult = RecipesApi.retrofitService.getRecipesByQuery(BuildConfig.WS_APP_ID, BuildConfig.WS_APP_KEY, query)
                _hitList.value = listResult.hits
                // ****RecyclerViewErrorHandling s4: Set the status to DONE if the call is successful and the values were updated
                _status.value = RecipesApiStatus.DONE
            } catch (e: Exception) {
                // ****RecyclerViewErrorHandling s5: Set the status to ERROR if the catch block is executed. Also clear the results
                _status.value = RecipesApiStatus.ERROR
                _hitList.value = emptyList()
            }
        }
        // ****Notifications s6: Send a notification in the ViewModel when a user searches for new recipes. We need to pass the Application variable and
        // the message to display in the Notification body. At this point if you run the app, the notification will not be shown, we need to configure the
        // channel in the next steps to be able to display the notification.
        val notificationManager = ContextCompat.getSystemService(
            app,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(app.getString(R.string.notification_message).format(query), app, showLargeImage = true, showAction = true)
    }

    // ****Notifications s18: Now you can dismiss all the previously created notifications by calling notificationManager.cancelNotifications(). In this example,
    // all the notifications will be dismissed when the user taps on any recipe from the list
    fun clearPreviousNotifications() {
        val notificationManager =
            ContextCompat.getSystemService(
                app,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelNotifications()
    }

    // ----------------------------- Database Methods (start)---------------------------------------

    // ****Room s16: In the ViewModel, add the function with the call that you want to make to the DB. It must be suspend to be able to use coroutines.
    private suspend fun getLastRecipeReviewFromDatabase(): RecipeReview? {
        return database.getLatestRecipeReview() //Room automatically sets the Dispatcher.IO. If not using Room, then the Dispatcher must be set: withContext(Dispatchers.IO) {
    }

    private suspend fun insert(review: RecipeReview) {
        database.insert(review)
    }

    fun initializeLastRecipeReview() {
        // ****Room s17: Call the suspend database function by using the coroutine and assign the result to the LiveData variable.
        viewModelScope.launch {
            _lastReviewedRecipe.value = getLastRecipeReviewFromDatabase()
        }
    }

    fun addReview(hit: HitDTO) {
        viewModelScope.launch {
            val newReview = RecipeReview()
            newReview.recipeName = hit.recipe.label
            newReview.rating = (0..5).random()
            newReview.comments = "None"
            newReview.imageUrl = hit.recipe.image
            newReview.realSpentTime = (0..120).random()
            insert(newReview)
            _lastReviewedRecipe.value = getLastRecipeReviewFromDatabase()
        }
    }

    // ----------------------------- Database Methods (end)-----------------------------------------



    //val q : String = savedStateHandle["q"] ?: //SavedStateHandle permite que ViewModel acceda al estado y los argumentos guardados del fragmento o actividad asociados.
    //throw IllegalArgumentException("missing query")


    //val recipeList : LiveData<SearchResponse> = RecipesRepository.getRecipes("coffe")

}