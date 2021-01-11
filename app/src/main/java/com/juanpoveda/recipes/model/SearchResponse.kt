package com.juanpoveda.recipes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// ****Retrofit s3: Create the Data Classes for the endpoint response objects
data class SearchResponse (
    @SerializedName("count") val count: Int,
    @SerializedName("from") val from: Int,
    @SerializedName("hits") val hits: List<Hit>,
    @SerializedName("more") val more: Boolean,
    @SerializedName("q") val q: String,
    @SerializedName("to") val to: Int
)

data class Hit (
    @SerializedName("bookmarked") val bookmarked: Boolean,
    @SerializedName("bought") val bought: Boolean,
    @SerializedName("recipe") val recipe: Recipe
): Serializable

data class Recipe (
    @SerializedName("calories") val calories: Float,
    @SerializedName("cautions") val cautions: List<String>,
    @SerializedName("cuisineType") val cuisineType: List<String>,
    @SerializedName("dietLabels") val dietLabels: List<String>,
    @SerializedName("digest") val digest: List<Digest>,
    @SerializedName("dishType") val dishType: List<String>,
    @SerializedName("healthLabels") val healthLabels: List<String>,
    @SerializedName("image") val image: String,
    @SerializedName("ingredientLines") val ingredientLines: List<String>,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("label") val label: String,
    @SerializedName("mealType") val mealType: List<String>,
    @SerializedName("shareAs") val shareAs: String,
    @SerializedName("source") val source: String,
    @SerializedName("totalTime") val totalTime: Int,
    @SerializedName("totalWeight") val totalWeight: Float,
    @SerializedName("uri") val uri: String,
    @SerializedName("url") val url: String,
    @SerializedName("yield") val yield: Int
)

data class Digest (
    @SerializedName("daily") val daily: Float,
    @SerializedName("hasRDI") val hasRDI: Boolean,
    @SerializedName("label") val label: String,
    @SerializedName("schemaOrgTag") val schemaOrgTag: String,
    @SerializedName("sub") val sub: List<Digest>,
    @SerializedName("tag") val tag: String,
    @SerializedName("total") val total: Float,
    @SerializedName("unit") val unit: String
)

data class Ingredient (
    @SerializedName("food") val food: String,
    @SerializedName("foodCategory") val foodCategory: String,
    @SerializedName("image") val image: String,
    @SerializedName("measure") val measure: String,
    @SerializedName("quantity") val quantity: Float,
    @SerializedName("text") val text: String,
    @SerializedName("weight") val weight: Float
)