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

        // ****SafeArgs s5: Get the passed args by using the auto-generated HitDetailFragmentArgs class. The args value will contain
        // all the previously defined args and is a HitDetailFragmentArgs object.
        val args = HitDetailFragmentArgs.fromBundle(requireArguments())

        binding.titleTextView.text = args.selectedHit.recipe.label

        binding.stepsButton.setOnClickListener {
            findNavController().navigate(R.id.action_hitDetailFragment_to_stepsFragment)
        }
        binding.ingredientDetailButton.setOnClickListener {
            findNavController().navigate(R.id.action_hitDetailFragment_to_ingredientDetailFragment)
        }
    }

}