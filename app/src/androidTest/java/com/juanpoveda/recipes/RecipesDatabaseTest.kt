package com.juanpoveda.recipes

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.juanpoveda.recipes.database.RecipeReview
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.database.RecipesDatabaseDAO
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
// ****InstrumentedTest s1: Add the class and annotate to Run it with AndroidJUnit4. An instrumented tet requires the Android Framework and needs an
// emulator of a physical device to run. In order to run the tests in the class just right-click in the class and select Run.
@RunWith(AndroidJUnit4::class)
class RecipesDatabaseTest {
    private lateinit var recipesDao: RecipesDatabaseDAO
    private lateinit var db: RecipesDatabase

    // ****InstrumentedTest s2: Define the steps that needs to be executed before the test with @Before
    // ****RoomTest s1: In order to use the DB, we need to create it following this process
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        recipesDao = db.recipesDatabaseDAO
    }

    // ****InstrumentedTest s3: Define the steps that needs to be executed after the test with @After
    // ****RoomTest s3: Wen no more transactions needed, close the DB
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    // ****InstrumentedTest s4: Define the Test with @Test, finish it with an assertion
    // ****RoomTest s2: To call the db functions that we defined in the DAO use the following syntax
    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetRecipeReview() {
        val review = RecipeReview()
        recipesDao.insert(review)
        val lastReview = recipesDao.getLatestRecipeReview()
        // If rating is -1 means that the field was inserted successfully because the default value of rating in the RecipeReview() call is -1
        assertEquals(lastReview?.rating, -1)
    }
}