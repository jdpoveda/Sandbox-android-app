package com.juanpoveda.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.RecipesApplication
import com.juanpoveda.recipes.adapter.RecipeFromRepoListAdapter
import com.juanpoveda.recipes.databinding.RecipeListFromRepoFragmentBinding
import com.juanpoveda.recipes.data.domain.RecipeDomain
import com.juanpoveda.recipes.viewmodel.RecipeListFromRepoViewModel
import com.juanpoveda.recipes.viewmodel.factory.RecipeListFromRepoViewModelFactory

class RecipeListFromRepoFragment : Fragment(), RecipeFromRepoListAdapter.OnRecipeFromRepoClickListener, SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = RecipeListFromRepoFragment()
    }

    private lateinit var viewModel: RecipeListFromRepoViewModel

    private var _binding: RecipeListFromRepoFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RecipeListFromRepoFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recipeSearchView.setOnQueryTextListener(this)

        val application = requireNotNull(this.activity).application
        // ****ImproveRepositoryWithDataSources s14: Pass the recipesRepository from the Application class when creating the ViewModel
        val viewModelFactory = RecipeListFromRepoViewModelFactory(application, (application as RecipesApplication).recipesRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeListFromRepoViewModel::class.java)

        val recipeFromRepoListAdapter = RecipeFromRepoListAdapter(this)
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recipesRecyclerView.adapter = recipeFromRepoListAdapter

        // ****Repository s12: Now, we only need to observe the list in the fragment and it'll updated automatically. If there's no network connection, the
        // list will contain the last known data that is stored in the DB
        //viewModel.recipes.observe(viewLifecycleOwner) {
        //    it?.let { it1 ->
        //        recipeFromRepoListAdapter.submitList(it1)
        //    }
        //}

        // ****ImproveRepositoryWithDataSources s17: We're going to observe now the recipeList using the new Repo:
        viewModel.recipeListNewRepo.observe(viewLifecycleOwner) {
            it?.let { it1 ->
                recipeFromRepoListAdapter.submitList(it1)
            }
        }

        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //viewModel.searchRecipesFromRepo(query.orEmpty())
        // ****ImproveRepositoryWithDataSources s18: We're going to search the recipes using the new Repo.
        viewModel.searchRecipesFromNewRepo(query.orEmpty())
        binding.root.requestFocus() // To avoid keeping the focus in the SearchView after submit
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onRecipeFromRepoClick(item: RecipeDomain) {
        TODO("Not yet implemented")
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            viewModel.onNetworkErrorShown()
        }
    }

}