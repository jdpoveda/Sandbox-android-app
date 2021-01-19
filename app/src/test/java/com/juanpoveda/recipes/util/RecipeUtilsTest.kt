package com.juanpoveda.recipes.util

import com.juanpoveda.recipes.network.Ingredient
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.startsWith
import org.junit.Assert.*
import org.junit.Test

// ****UnitTest s2: This class was generated from the previous step.
class RecipeUtilsTest {
    // ****UnitTest s3: Create the test for each function of the class (as long as they're not depending on Android framework). The naming convention
    // should be descriptive: subjectUnderTest_actionOrInput_expectedResult. Each test must have the tag @Test
    @Test
    fun addArrowToIngredientsList_listOfTwoIngredients_returnsIngredientListWithArrows() {
        // ****UnitTest s4: Initialize the test objects. In this example, a list of ingredients is needed to perform the test
        val ingredients = listOf(
            Ingredient(
                customText = "",
                food = "Pork",
                foodCategory = "Meat",
                measure = "1.2Kg",
                quantity = 1200F,
                text = "Pork meat",
                weight = 5F,
                image = ""
            ),
            Ingredient(
                customText = "",
                food = "Chicken",
                foodCategory = "Meat",
                measure = "2.3Kg",
                quantity = 2300F,
                text = "Chicken meat",
                weight = 4F,
                image = ""
            )
        )
        // ****UnitTest s5: Call the function to test
        val result = addArrowToIngredientsList(ingredients)

        // ****UnitTest s6: Check the result using assertion. Run the test and check if it works.
        //assert(result[0].customText.startsWith("->"))
        //assert(result[1].customText.startsWith("->"))

        // ****UnitTest s8: Change the old assertions to work with Hamcrest, note that they're more readable to the user.
        assertThat(result[0].customText, startsWith("->"))
        assertThat(result[1].customText, startsWith("->"))
    }

    // ****UnitTest s9: Add more test scenarios for this class
    @Test
    fun addArrowToIngredientsList_emptyList_returnsEmptyList() {
        val ingredients = listOf<Ingredient>()
        val result = addArrowToIngredientsList(ingredients)

        assertThat(result.size, `is`(0))
    }

}