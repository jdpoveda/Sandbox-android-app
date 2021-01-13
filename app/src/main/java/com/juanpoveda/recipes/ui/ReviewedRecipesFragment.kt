package com.juanpoveda.recipes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.adapter.HitListAdapter
import com.juanpoveda.recipes.adapter.RecipeReviewListAdapter
import com.juanpoveda.recipes.database.RecipeReview
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.databinding.RecipeListFragmentBinding
import com.juanpoveda.recipes.databinding.ReviewedRecipesFragmentBinding
import com.juanpoveda.recipes.model.Hit
import com.juanpoveda.recipes.viewmodel.HomeViewModel
import com.juanpoveda.recipes.viewmodel.ReviewedRecipesViewModel
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

        viewModel.reviewedRecipes.observe(viewLifecycleOwner) {
            it?.let { it1 ->
                this.recipesReviewedListAdapter = RecipeReviewListAdapter(it1, this)
                binding.recipesReviewedRecyclerView.setHasFixedSize(true)
                binding.recipesReviewedRecyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recipesReviewedRecyclerView.adapter = this.recipesReviewedListAdapter
                (binding.recipesReviewedRecyclerView.adapter as RecipeReviewListAdapter?)?.notifyDataSetChanged()
            }
        }
    }

    override fun onRecipeReviewClick(item: RecipeReview) {
        Toast.makeText(activity, "Recipe review clicked!", Toast.LENGTH_SHORT).show()
    }

}