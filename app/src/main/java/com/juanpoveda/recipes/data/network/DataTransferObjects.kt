package com.juanpoveda.recipes.data.network

import com.google.gson.annotations.SerializedName
import com.juanpoveda.recipes.data.database.DatabaseRecipe
import com.juanpoveda.recipes.data.domain.RecipeDomain
import java.io.Serializable

// ****Retrofit s3: Create the Data Classes for the endpoint response objects
data class SearchResponseDTO (
    @SerializedName("count") val count: Int,
    @SerializedName("from") val from: Int,
    @SerializedName("hits") val hits: List<HitDTO>,
    @SerializedName("more") val more: Boolean,
    @SerializedName("q") val q: String,
    @SerializedName("to") val to: Int
)

data class HitDTO (
    @SerializedName("bookmarked") val bookmarked: Boolean,
    @SerializedName("bought") val bought: Boolean,
    @SerializedName("recipe") val recipe: RecipeDTO
): Serializable

data class RecipeDTO (
    @SerializedName("calories") val calories: Float,
    @SerializedName("cautions") val cautions: List<String>,
    @SerializedName("cuisineType") val cuisineType: List<String>,
    @SerializedName("dietLabels") val dietLabels: List<String>,
    @SerializedName("digest") val digest: List<DigestDTO>,
    @SerializedName("dishType") val dishType: List<String>,
    @SerializedName("healthLabels") val healthLabels: List<String>,
    @SerializedName("image") val image: String,
    @SerializedName("ingredientLines") val ingredientLines: List<String>,
    @SerializedName("ingredients") val ingredients: List<IngredientDTO>,
    @SerializedName("label") val label: String,
    @SerializedName("mealType") val mealType: List<String>,
    @SerializedName("shareAs") val shareAs: String,
    @SerializedName("source") val source: String,
    @SerializedName("totalTime") val totalTime: Int,
    @SerializedName("totalWeight") val totalWeight: Float,
    @SerializedName("uri") val uri: String,
    @SerializedName("url") val url: String,
    @SerializedName("yield") val yield: Float
): Serializable

data class DigestDTO (
    @SerializedName("daily") val daily: Float,
    @SerializedName("hasRDI") val hasRDI: Boolean,
    @SerializedName("label") val label: String,
    @SerializedName("schemaOrgTag") val schemaOrgTag: String,
    @SerializedName("sub") val sub: List<DigestDTO>,
    @SerializedName("tag") val tag: String,
    @SerializedName("total") val total: Float,
    @SerializedName("unit") val unit: String
): Serializable

data class IngredientDTO (
    @SerializedName("food") val food: String,
    @SerializedName("foodCategory") val foodCategory: String,
    @SerializedName("image") val image: String,
    @SerializedName("measure") val measure: String,
    @SerializedName("quantity") val quantity: Float,
    @SerializedName("text") val text: String,
    @SerializedName("weight") val weight: Float,
    var customText: String
): Serializable


// ****Repository s3: Add the functions to convert the Network objects to domain and database models.

fun SearchResponseDTO.asDomainModel(): List<RecipeDomain> {
    return hits.map {
        it.recipe.asDomainModel()
    }
}

fun SearchResponseDTO.asDatabaseModel(): List<DatabaseRecipe> {
    return hits.map {
        it.recipe.asDatabaseModel()
    }
}

fun RecipeDTO.asDomainModel(): RecipeDomain {
    return RecipeDomain(
        calories = calories,
        image = image,
        label = label,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        //ingredients = ingredientsDomain,
        url = url,
        uri = uri
    )
}

fun RecipeDTO.asDatabaseModel(): DatabaseRecipe {
    return DatabaseRecipe(
        calories = calories,
        image = image,
        //ingredientLines = ingredientLines,
        label = label,
        //mealType = mealType,
        shareAs = shareAs,
        source = source,
        totalTime = totalTime,
        totalWeight = totalWeight,
        //ingredients = ingredientsDatabase,
        url = url,
        //cautions = cautions,
        `yield` = `yield`,
        //cuisineType = cuisineType,
        //dietLabels = dietLabels,
        //digest = digest,
        //dishType = dishType,
        //healthLabels = healthLabels,
        uri = uri
    )
}

fun HitDTO.asDomainModel(): RecipeDomain {
    return RecipeDomain(
        calories = recipe.calories,
        image = recipe.image,
        label = recipe.label,
        shareAs = recipe.shareAs,
        source = recipe.source,
        totalTime = recipe.totalTime,
        totalWeight = recipe.totalWeight,
        url = recipe.url,
        uri = recipe.uri
    )
}