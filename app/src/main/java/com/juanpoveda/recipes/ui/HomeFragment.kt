package com.juanpoveda.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.juanpoveda.recipes.databinding.HomeFragmentBinding
import com.juanpoveda.recipes.viewmodel.HomeViewModel

// ****MVVM s2: Generate this Fragment (View) and the corresponding ViewModel by right click
// on the package -> New -> Fragment -> Fragment (With ViewModel).
// This action will generate HomeViewModel class also.
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    // ****MVVM s5: create a class val for the viewModel and initialize it by using: viewModel: HomeViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()

    // ****ViewBinding s2: As we have viewBinding = true in the app/gradle file, the Binging classes
    // like HomeFragmentBindingare generated. Create a field in the Fragment this way:
    private var _binding: HomeFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ****ViewBinding s3: The inflate layout changes a little bit, here is the example for a Fragment (It's slighly different for an Activity!).
        _binding = HomeFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        // ****MVVM s6: Now, the viewModel can be used to observe the LiveData fields and manage the UI according to the changes in the data.
        viewModel.recipeList.observe(viewLifecycleOwner) {
            // ****ViewBinding s4: We can call the views of the layout directly with binding.viewName
            binding.homeText.text = it.q
            println("******* hits " + it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}