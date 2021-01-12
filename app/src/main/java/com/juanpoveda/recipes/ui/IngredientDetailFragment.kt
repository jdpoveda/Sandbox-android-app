package com.juanpoveda.recipes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.adapter.HitListAdapter
import com.juanpoveda.recipes.adapter.IngredientListAdapter
import com.juanpoveda.recipes.databinding.HomeFragmentBinding
import com.juanpoveda.recipes.databinding.IngredientDetailFragmentBinding
import com.juanpoveda.recipes.model.Ingredient
import com.juanpoveda.recipes.viewmodel.IngredientDetailViewModel

class IngredientDetailFragment : Fragment(), IngredientListAdapter.OnIngredientClickListener {

    companion object {
        fun newInstance() = IngredientDetailFragment()
    }

    private val viewModel: IngredientDetailViewModel by viewModels()
    private var _binding: IngredientDetailFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!
    private var ingredientListAdapter: IngredientListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = IngredientDetailFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = IngredientDetailFragmentArgs.fromBundle(requireArguments())
        // TODO: Use the ViewModel, don't pass the data from the bundle, the data needs to be saved in the viewmodel!

        this.ingredientListAdapter = IngredientListAdapter(args.recipe.ingredients, this)
        binding.ingredientListRecyclerView.setHasFixedSize(true)
        binding.ingredientListRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.ingredientListRecyclerView.adapter = this.ingredientListAdapter
        (binding.ingredientListRecyclerView.adapter as IngredientListAdapter?)?.notifyDataSetChanged()

    }

    override fun onIngredientClick(item: Ingredient) {
        TODO("Not yet implemented")
    }

}