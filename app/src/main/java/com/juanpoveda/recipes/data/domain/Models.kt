package com.juanpoveda.recipes.data.domain

import com.juanpoveda.recipes.data.database.DatabaseRecipe
import java.io.Serializable

data class RecipeDomain (
    val calories: Float,
    val image: String,
    //val ingredientLines: List<String>,
    //val ingredients: List<IngredientDomain>,
    val label: String,
    //val mealType: List<String>,
    val shareAs: String,
    val source: String,
    val totalTime: Int,
    val totalWeight: Float,
    val url: String,
    val uri: String
): Serializable

data class IngredientDomain (
    val food: String,
    val foodCategory: String,
    val image: String,
    val measure: String,
    val quantity: Float,
    val text: String,
    val weight: Float,
    var customText: String
): Serializable

fun RecipeDomain.asDatabaseModel(): DatabaseRecipe {
    return DatabaseRecipe(
        calories = calories,
        image = image,
        label = label,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        url = url,
        uri = uri,
        `yield` = 0F
    )
}