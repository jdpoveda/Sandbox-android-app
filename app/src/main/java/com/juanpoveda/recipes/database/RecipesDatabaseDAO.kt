package com.juanpoveda.recipes.database

import androidx.lifecycle.LiveData
import androidx.room.*

// ****Room s3: Add the DAO as an Interface. The DAO provides convenience methods for inserting, deleting, and updating the database and must include @Dao annotation.
@Dao
interface RecipesDatabaseDAO {

    // ****Room s4: Add the operations that you'll need to use and annotate them, for this methods, Room will generate all the necessary code to
    // perform the operations. Room has 3 predefined annotations: Insert, Update and Delete a specific item:
    @Insert
    suspend fun insert(recipeReview: RecipeReview) // ****Room s15: make all the DAO functions that don't return a LiveData as suspend to be able to use coroutines.

    @Update
    suspend fun update(night: RecipeReview)

    @Delete
    suspend fun delete(night: RecipeReview)

    // ****Room s5: For additional operations, an explicit SQL query must be included
    @Query("SELECT * from recipe_review_table WHERE id = :key")
    suspend fun get(key: Long): RecipeReview?

    @Query("DELETE FROM recipe_review_table") //Deletes all the registers
    suspend fun clear()

    @Query("SELECT * FROM recipe_review_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatestRecipeReview(): RecipeReview?

    // ****Room s6: LiveData can be included in Room operations! Room keeps this LiveData updated for you, which means you only need to explicitly get the data once.
    @Query("SELECT * FROM recipe_review_table ORDER BY id DESC")
    fun getAllRecipeReviews(): LiveData<List<RecipeReview>>
}