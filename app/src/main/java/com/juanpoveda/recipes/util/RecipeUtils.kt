package com.juanpoveda.recipes.util

import com.juanpoveda.recipes.data.network.IngredientDTO

// ****UnitTest s1: This kind of tests should be used to test logic that doesn't need any Android framework or device to run. To create a test for a class or function,
// right click, generate, test. After that, a new class (RecipeUtilsTest) will be generated under tes/util/ dir. Remember that unit tests should be in test/.
internal fun addArrowToIngredientsList(ingredients: List<IngredientDTO>): List<IngredientDTO> {
    return ingredients.map{ingredient ->
        var ing = ingredient
        ing.customText = "-> " + ingredient.text
        ing }
}