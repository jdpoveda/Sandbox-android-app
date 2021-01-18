package com.juanpoveda.recipes.database

import androidx.room.*
import com.juanpoveda.recipes.domain.IngredientDomain
import com.juanpoveda.recipes.domain.RecipeDomain
import com.juanpoveda.recipes.network.Digest
import com.juanpoveda.recipes.network.Ingredient
import com.juanpoveda.recipes.network.Recipe

// ****Room s2: Add Entities and mark the Entity, PrimaryKey and Columns with the following annotations
@Entity(tableName = "recipe_review_table")
data class RecipeReview (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "recipe_name")
    var recipeName: String = "",
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",
    @ColumnInfo(name = "rating")
    var rating: Int = -1,
    @ColumnInfo(name = "real_spent_time")
    var realSpentTime: Int = -1,
    @ColumnInfo(name = "comments")
    var comments: String = ""
)


// ****Repository s1: Create the database entities that will store the data in Room. In this scenario, we're going to get a list of Recipe with a network call
// and we want to cache that list, so we create the DatabaseRecipe entity with the same fields that Recipe.
@Entity(tableName = "recipe_table")
data class DatabaseRecipe constructor (
    @PrimaryKey
    var url: String,
    var calories: Float,
    //var cautions: List<String>,
    //var cuisineType: List<String>,
    //var dietLabels: List<String>,
    //var digest: List<Digest>,
    //var dishType: List<String>,
    //var healthLabels: List<String>,
    @ColumnInfo(name = "image")
    var image: String,
    //var ingredientLines: List<String>,
    //var ingredients: List<DatabaseIngredient>,
    @ColumnInfo(name = "label")
    var label: String,
    //var mealType: List<String>,
    @ColumnInfo(name = "share_as")
    var shareAs: String,
    @ColumnInfo(name = "source")
    var source: String,
    @ColumnInfo(name = "total_time")
    var totalTime: Int,
    @ColumnInfo(name = "total_weight")
    var totalWeight: Float,
    @ColumnInfo(name = "uri")
    var uri: String,
    @ColumnInfo(name = "yield")
    var yield: Float
)

/*
@Entity(tableName = "ingredient_table")
data class DatabaseIngredient constructor (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var food: String,
    var foodCategory: String,
    var image: String,
    var measure: String,
    var quantity: Float,
    var text: String,
    var weight: Float,
    var customText: String
) */

// ****Repository s2: Add a method to convert a list of database objects into a list of domain model objects, mapping all the necessary attributes.
fun List<DatabaseRecipe>.asDomainModel(): List<RecipeDomain> {

    return map {
        RecipeDomain(
            calories = it.calories,
            image = it.image,
            label = it.label,
            shareAs = it.shareAs,
            totalTime = it.totalTime,
            source = it.source,
            totalWeight = it.totalWeight,
            url = it.url
        )
    }
}
