package com.juanpoveda.recipes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.juanpoveda.recipes.databinding.StepsFragmentBinding
import com.juanpoveda.recipes.viewmodel.StepsViewModel

class StepsFragment : Fragment() {

    companion object {
        fun newInstance() = StepsFragment()
    }

    private val viewModel: StepsViewModel by viewModels()
    private var _binding: StepsFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = StepsFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = StepsFragmentArgs.fromBundle(requireArguments())
        binding.recipeTitleTextView.text = args.recipe.ingredients.toString()
        // TODO: Use the ViewModel
    }

}