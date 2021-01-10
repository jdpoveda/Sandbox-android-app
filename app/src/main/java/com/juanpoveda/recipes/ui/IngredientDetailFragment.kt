package com.juanpoveda.recipes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.databinding.HomeFragmentBinding
import com.juanpoveda.recipes.databinding.IngredientDetailFragmentBinding
import com.juanpoveda.recipes.viewmodel.IngredientDetailViewModel

class IngredientDetailFragment : Fragment() {

    companion object {
        fun newInstance() = IngredientDetailFragment()
    }

    private lateinit var viewModel: IngredientDetailViewModel
    private var _binding: IngredientDetailFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = IngredientDetailFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IngredientDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}