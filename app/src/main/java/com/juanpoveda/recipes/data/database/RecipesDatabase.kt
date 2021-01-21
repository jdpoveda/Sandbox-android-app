package com.juanpoveda.recipes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// ****Room s7: Create an abstract class for the database, it must be annotated with @Database and extends RoomDatabase.
// The entities list must be passed as a param in the annotation, with these params:
// - version as 1. Whenever you change the schema, you'll have to increase the version number.
// - exportSchema to false, so as not to keep schema version history backups.
// NOTE: You only need one instance of the Room database for the whole app, so make the RoomDatabase a singleton.
@Database(entities = [RecipeReview::class, DatabaseRecipe::class], version = 7, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    // ****Room s8: Add the DAO as an abstract field of the class. Multiple DAOs are allowed.
    abstract val recipesDatabaseDAO: RecipesDatabaseDAO

    companion object {
        // ****Room s9: Add an instance variable of RecipesDatabase. This one must be Volatile because we need to ensure that its value is always
        // up-to-date (The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory)
        @Volatile
        private var INSTANCE: RecipesDatabase? = null

        fun getInstance(context: Context): RecipesDatabase {

            // ****Room s10: Add a synchronized block. Here's is why:
            // Multiple threads can potentially ask for a database instance at the same time, resulting in two databases instead of one.
            // This problem is not likely to happen in this sample app, but it's possible for a more complex app. Wrapping the code to get the database
            // into synchronized means that only one thread of execution at a time can enter this block of code, which makes sure the database only
            // gets initialized once.
            synchronized(this) {
                // ****Room s11: Copy the value of instance to a local variable to take advantage of smart-cast that is only available for local variables.
                var instance = INSTANCE
                // ****Room s12: Build the database instance if it's null. Note the .fallbackToDestructiveMigration() statement, Normally, you would have to
                // provide a migration object with a migration strategy for when the schema changes. In this case is to delete all the database
                // and create a new schema.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipesDatabase::class.java,
                        "recipes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}