package com.juanpoveda.recipes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.databinding.HitDetailFragmentBinding
import com.juanpoveda.recipes.databinding.HomeFragmentBinding
import com.juanpoveda.recipes.databinding.IngredientDetailFragmentBinding
import com.juanpoveda.recipes.viewmodel.HitDetailViewModel

class HitDetailFragment : Fragment() {

    companion object {
        fun newInstance() = HitDetailFragment()
    }

    private lateinit var viewModel: HitDetailViewModel
    private var _binding: HitDetailFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = HitDetailFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HitDetailViewModel::class.java)
        // TODO: Use the ViewModel

        binding.stepsButton.setOnClickListener {
            findNavController().navigate(R.id.action_hitDetailFragment_to_stepsFragment)
        }
        binding.ingredientDetailButton.setOnClickListener {
            findNavController().navigate(R.id.action_hitDetailFragment_to_ingredientDetailFragment)
        }
    }

}