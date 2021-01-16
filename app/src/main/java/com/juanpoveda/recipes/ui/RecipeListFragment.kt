package com.juanpoveda.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.adapter.HitListAdapter
import com.juanpoveda.recipes.database.RecipesDatabase
import com.juanpoveda.recipes.databinding.RecipeListFragmentBinding
import com.juanpoveda.recipes.network.Hit
import com.juanpoveda.recipes.viewmodel.HomeViewModel
import com.juanpoveda.recipes.viewmodel.RecipesApiStatus
import com.juanpoveda.recipes.viewmodel.factory.HomeViewModelFactory

// ****ViewModel s1: Generate this Fragment (View) and the corresponding ViewModel by right click
// on the package -> New -> Fragment -> Fragment (With ViewModel).
// This action will generate HomeViewModel class also.
class RecipeListFragment : Fragment(), HitListAdapter.OnHitClickListener, SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = RecipeListFragment()
    }

    // ****ViewModel s6: There are 2 options:
    // 1. If you don't need to pass any param to the ViewModel: Create a class val for the viewModel and initialize it by using:
    // private val viewModel: HomeViewModel by viewModels() and that's all - no need to set the viewModel value later in the onActivityCreated method.
    // 2. If some param must be passed to the ViewModel, a Factory class must be created and you need to add the viewModel var as lateinit to initialize
    // it later in the onActivityCreated method.
    private lateinit var viewModel: HomeViewModel
    // ****RecyclerView s4: Create a field for the adapter in the Activity/Fragment
    private var hitListAdapter: HitListAdapter? = null

    // ****ViewBindingFragment s2: As we have viewBinding = true in the app/gradle file, the Binging classes
    // like HomeFragmentBindingare generated. Create a field in the Fragment this way:
    private var _binding: RecipeListFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ****ViewBindingFragment s3: The inflate layout changes a little bit, here is the example for a Fragment (It's slighly different for an Activity!).
        _binding = RecipeListFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // ****ViewBindingFragment s4: We can call the views of the layout directly with binding.viewName
        binding.recipeSearchView.setOnQueryTextListener(this)

        // ****ViewModel s7: You don't need to add anything if you took the option 1 in the previous step. However, If you took the option 2,
        // then you need to initialize the viewModel and pass all the required params this way:
        val application = requireNotNull(this.activity).application
        val dataSource = RecipesDatabase.getInstance(application).recipesDatabaseDAO
        val viewModelFactory = HomeViewModelFactory(dataSource, application) // ****Room s14: Pass the DAO as argument in the ViewModels where you want to use the database.
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // ****ViewModel s8: Now, the viewModel can be used to observe the LiveData fields
        viewModel.hitList.observe(viewLifecycleOwner) {
            // ****RecyclerView s5: Instantiate the adapter, pass the clickListener implemented in the Fragment and the list of items.
            it?.let { it1 ->
                this.hitListAdapter = HitListAdapter(it1, this)
                binding.recipesRecyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recipesRecyclerView.adapter = this.hitListAdapter
                (binding.recipesRecyclerView.adapter as HitListAdapter?)?.notifyDataSetChanged()
            }
        }

        // ****RecyclerViewErrorHandling s6: Observe the status in the View and enable/disable the loading/error images according to the scenario.
        viewModel.status.observe(viewLifecycleOwner) {
            when(it) {
                RecipesApiStatus.LOADING -> {
                    binding.stateImageView.visibility = View.VISIBLE
                    binding.stateImageView.setImageResource(R.drawable.loading_animation)
                }
                RecipesApiStatus.ERROR -> {
                    binding.stateImageView.visibility = View.VISIBLE
                    binding.stateImageView.setImageResource(R.drawable.ic_connection_error)
                }
                RecipesApiStatus.DONE -> {
                    binding.stateImageView.visibility = View.GONE

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHitClick(item: Hit) {
        Toast.makeText(activity, "Hit clicked!", Toast.LENGTH_SHORT).show()
        // ****Navigation s7: To navigate between fragments, use this syntax and the previously created action. If data must be passed between
        // fragments, check ****SafeArgs. The following line is commented because in this fragment we want to pass data to the next fragment.
        //findNavController().navigate(R.id.action_homeFragment_to_hitDetailFragment)

        // ****SafeArgs s4: Change the call to navigate(). Normally, the call requires only the id of the action, like
        // .navigate(R.id.action_homeFragment_to_hitDetailFragment) but now it must include the Directions class and the specific action, like
        // .navigate(HomeFragmentDirections.actionHomeFragmentToHitDetailFragment(item)). Note that the call will expect the argument that
        // were defined in the previous step.
        findNavController().navigate(RecipeListFragmentDirections.actionRecipeListFragmentToHitDetailFragment(item))
    }

    override fun onAddReviewClick(item: Hit) {
        Toast.makeText(activity, "Add review clicked!", Toast.LENGTH_SHORT).show()
        viewModel.addReview(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // ****ViewModel s9: The View must inform the viewModel if the user entered new information, like this search. The ViewModel must have
        // methods to manage this and update the MutableLiveData so the UI can get the updates in the Observer
        viewModel.searchHits(query.orEmpty())
        binding.root.requestFocus() // To avoid keeping the focus in the SearchView after submit
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}