package com.juanpoveda.recipes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.adapter.RecipeReviewListAdapter
import com.juanpoveda.recipes.database.RecipeReview
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.databinding.ReviewedRecipesFragmentBinding
import com.juanpoveda.recipes.viewmodel.HomeViewModel
import com.juanpoveda.recipes.viewmodel.factory.HomeViewModelFactory

class ReviewedRecipesFragment : Fragment(), RecipeReviewListAdapter.OnRecipeReviewClickListener {

    companion object {
        fun newInstance() = ReviewedRecipesFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var recipesReviewedListAdapter: RecipeReviewListAdapter? = null

    private var _binding: ReviewedRecipesFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ReviewedRecipesFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = RecipesDatabase.getInstance(application).recipesDatabaseDAO
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // ****RecyclerViewGridLayout s1: Add a GridLayoutManager to the Recyclerview's layoutManager instead the LinearLayoutManager. The spanCount is the
        // number of items that you want to display per row.
        // To make a Grid with horizontal scrolling: GridLayoutManager(activity, 4, GridLayoutManager.HORIZONTAL, false)
        binding.recipesReviewedRecyclerView.layoutManager = GridLayoutManager(activity, 4)

        viewModel.reviewedRecipes.observe(viewLifecycleOwner) {
            it?.let { it1 ->
                this.recipesReviewedListAdapter = RecipeReviewListAdapter(it1, this)
                binding.recipesReviewedRecyclerView.adapter = this.recipesReviewedListAdapter
                (binding.recipesReviewedRecyclerView.adapter as RecipeReviewListAdapter?)?.notifyDataSetChanged()
            }
        }
    }

    override fun onRecipeReviewClick(item: RecipeReview) {
        Toast.makeText(activity, "Recipe review clicked!", Toast.LENGTH_SHORT).show()
    }

}