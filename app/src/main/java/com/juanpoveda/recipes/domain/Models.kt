package com.juanpoveda.recipes.domain

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
    val url: String
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