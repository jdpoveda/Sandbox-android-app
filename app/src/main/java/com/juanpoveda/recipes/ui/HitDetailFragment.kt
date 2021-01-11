package com.juanpoveda.recipes.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

    // ----------------------------- Lifecycle Methods (start)--------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = HitDetailFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        // ****ShareWithOtherApps s1: Enable options menu to see the share button in the Toolbar.
        setHasOptionsMenu(true)
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

    // ----------------------------- Lifecycle Methods (end)----------------------------------------

    // ----------------------------- Options menu Methods (start)-----------------------------------

    // ****ShareWithOtherApps s5: Show the Share Menu Item Dynamically
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.hit_detail_menu, menu)
        if(getShareIntent().resolveActivity(requireActivity().packageManager)==null){
            menu.findItem(R.id.share).isVisible = false
        }
    }

    // ****ShareWithOtherApps s6: Listen for the share button click and send the sharing intent when pressed
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){ // To get the id of the item (defined in the menu layout)
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    // ----------------------------- Options menu Methods (end)-------------------------------------

    // ----------------------------- Share content with other apps (start)--------------------------

    // ****ShareWithOtherApps s2: Create the share intent in the Fragment that will handle the share listener. This is an Implicit intent because
    // no specific Activity is included (the system will show a list of apps that can handle this to the user).
    private fun getShareIntent() : Intent {
        val args = HitDetailFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_title, args.selectedHit.recipe.label))
        return shareIntent
    }

    // ****ShareWithOtherApps s3: Add method to tarting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // ----------------------------- Share content with other apps (end)----------------------------

}