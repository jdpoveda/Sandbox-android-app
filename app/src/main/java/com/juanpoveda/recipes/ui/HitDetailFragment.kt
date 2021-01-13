package com.juanpoveda.recipes.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.databinding.HitDetailFragmentBinding
import com.juanpoveda.recipes.viewmodel.HitDetailViewModel

class HitDetailFragment : Fragment() {

    companion object {
        fun newInstance() = HitDetailFragment()
    }

    private val viewModel: HitDetailViewModel by viewModels()
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

        // ****SafeArgs s5: Get the passed args by using the auto-generated HitDetailFragmentArgs class. The args value will contain
        // all the previously defined args and is a HitDetailFragmentArgs object.
        val args = HitDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.setNewHit(args.selectedHit)

        viewModel.selectedHit.observe(viewLifecycleOwner) {
            binding.titleTextView.text = it.recipe.label
            binding.caloriesTextView.text = getString(R.string.recipe_calories_value).format(it.recipe.calories)
            binding.totalTimeTextView.text = getString(R.string.recipe_time_value).format(it.recipe.totalTime)
            Glide.with(binding.root).load(args.selectedHit.recipe.image).into(binding.recipeDetailImageView)
        }

        // ****NavigationWithLiveData s5: Observe the navigation variable of the ViewModel and launch the navigation event when it changes.
        // Don't forget to clear the navigation variable at the end!!!
        viewModel.navigateToStepsFragment.observe(viewLifecycleOwner) {recipe ->
            recipe?.let { //Here the ? operator is needed because recipe can be null and it'll cause a crash
                findNavController().navigate(HitDetailFragmentDirections.actionHitDetailFragmentToStepsFragment(recipe))
                viewModel.doneNavigatingToStepsFragment()
            }
        }
        binding.stepsButton.setOnClickListener {
            // ****NavigationWithLiveData s6: Call the launchNavigation method in the ViewModel
            viewModel.launchNavigationToStepsFragment(args.selectedHit.recipe)
        }
        binding.ingredientDetailButton.setOnClickListener {
            findNavController().navigate(HitDetailFragmentDirections.actionHitDetailFragmentToIngredientDetailFragment(args.selectedHit.recipe))
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