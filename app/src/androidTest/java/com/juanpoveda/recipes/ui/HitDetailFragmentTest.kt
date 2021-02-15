package com.juanpoveda.recipes.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.TestingObjectsInstrumented
import com.juanpoveda.recipes.data.network.HitDTO
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

// ****IntegrationTestEspresso s2: Generate the Test class for the Fragment by right-click in the Fragment class name, Generate, Test. It should
// be places in androidTest because this is an instrumented test. Add the annotations also.
@MediumTest // Marks the test as a "medium run-time" integration test (versus @SmallTest unit tests and @LargeTest end-to-end tests). This helps you group and choose which size of test to run.
@RunWith(AndroidJUnit4::class) // Used in any class using AndroidX Test.
class HitDetailFragmentTest {
    // ****IntegrationTestEspresso s3: Add the test to launch the fragment with launchFragmentInContainer
    @Test
    fun recipeDetails_DisplayedInUi() {
        // GIVEN - Add recipe to the DB
        val activeRecipe = TestingObjectsInstrumented.HIT_DTO_1

        // WHEN - HitDetailFragment fragment launched to display the recipe
        //val bundle = HitDetailFragmentArgs(argsMap).toBundle()
        val bundle = bundleOf("selectedHit" to activeRecipe)
        launchFragmentInContainer<HitDetailFragment>(bundle, R.style.Theme_Recipes)

        // ****IntegrationTestEspresso s4: Add the asserts with espresso, in this case we're checking for the title textView to be displayed and
        // to have the correct string.
        // THEN - Recipe details are displayed on the screen
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTextView)).check(matches(withText(activeRecipe.recipe.label)))

    }
}