package com.juanpoveda.recipes

import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.data.domain.asDTO
import com.juanpoveda.recipes.data.network.HitDTO

object TestingObjects {
    val RECIPE_1 = RecipeDomain(
        calories = 100F,
        image = "https://www.edamam.com/web-img/a1d/a1defc00162e8000b02866247fc03616.jpg",
        label = "Coffe Mousse Recipe",
        source = "Group Recipes",
        shareAs = "http://www.edamam.com/recipe/coffe-mousse-recipe-2b088b11332df9e1009f185109029dbe/coffe/balanced",
        totalWeight = 738.1310769624845F,
        totalTime = 20,
        uri = "http://www.edamam.com/ontologies/edamam.owl#recipe_2b088b11332df9e1009f185109029dbe",
        url = "http://www.grouprecipes.com/59195/coffe-mousse.html"
    )
    val RECIPE_2 = RecipeDomain(
        calories = 2076.588500006169F,
        image = "https://www.edamam.com/web-img/090/0902c9eb9cab75a312d5c79bf9bfd825",
        label = "Caramel Macchiato French Toast Cups recipes",
        source = "delightfulemade.com",
        shareAs = "http://www.edamam.com/recipe/caramel-macchiato-french-toast-cups-recipes-c1158f2601f74f47726f4c32ddf5503a/coffe/balanced",
        totalWeight = 718.2500000025613F,
        totalTime = 32,
        uri = "http://www.edamam.com/ontologies/edamam.owl#recipe_c1158f2601f74f47726f4c32ddf5503a",
        url = "http://delightfulemade.com/2015/10/26/caramel-macchiato-french-toast-cups/"
    )
    val RECIPE_3 = RecipeDomain(
        calories = 1149.0368005259847F,
        image = "https://www.edamam.com/web-img/dec/dec7cc25c3582bf2de0baa8b1fec0d44.jpg",
        label = "Kid-Friendly Pumpkin Spice Latte (No Coffe",
        source = "chelseasmessyapron.com",
        shareAs = "http://www.edamam.com/recipe/kid-friendly-pumpkin-spice-latte-no-coffe-3af155f0ad8f2ad46f4de0405151fdab/coffe/balanced",
        totalWeight = 1349.573999583521F,
        totalTime = 0,
        uri = "http://www.edamam.com/ontologies/edamam.owl#recipe_3af155f0ad8f2ad46f4de0405151fdab",
        url = "http://www.chelseasmessyapron.com/kid-friendly-pumpkin-spice-latte-no-coffee/"
    )
    val HIT_DTO_1 = HitDTO(
        bookmarked = false,
        bought = false,
        recipe = RECIPE_1.asDTO()
    )
    val HIT_DTO_2 = HitDTO(
        bookmarked = false,
        bought = false,
        recipe = RECIPE_2.asDTO()
    )

}