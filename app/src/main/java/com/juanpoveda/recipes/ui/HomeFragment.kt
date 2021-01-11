package com.juanpoveda.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.adapter.HitListAdapter
import com.juanpoveda.recipes.databinding.HomeFragmentBinding
import com.juanpoveda.recipes.model.Hit
import com.juanpoveda.recipes.viewmodel.HomeViewModel

// ****ViewModel s1: Generate this Fragment (View) and the corresponding ViewModel by right click
// on the package -> New -> Fragment -> Fragment (With ViewModel).
// This action will generate HomeViewModel class also.
class HomeFragment : Fragment(), HitListAdapter.OnHitClickListener, SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    // ****ViewModel s5: Create a class val for the viewModel and initialize it by using: viewModel: HomeViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()
    // ****RecyclerView s4: Create a field for the adapter in the Activity/Fragment
    private var hitListAdapter: HitListAdapter? = null

    // ****ViewBindingFragment s2: As we have viewBinding = true in the app/gradle file, the Binging classes
    // like HomeFragmentBindingare generated. Create a field in the Fragment this way:
    private var _binding: HomeFragmentBinding? = null //View binding to replace findViewById
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ****ViewBindingFragment s3: The inflate layout changes a little bit, here is the example for a Fragment (It's slighly different for an Activity!).
        _binding = HomeFragmentBinding.inflate(layoutInflater, container, false) //Inflate with HomeFragmentBinding as we want to bind bind the view
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // ****ViewBindingFragment s4: We can call the views of the layout directly with binding.viewName
        binding.recipeSearchView.setOnQueryTextListener(this)

        // ****ViewModel s6: Now, the viewModel can be used to observe the LiveData fields
        viewModel.hitList.observe(viewLifecycleOwner) {
            // ****RecyclerView s5: Instantiate the adapter, pass the clickListener implemented in the Fragment and the list of items.
            it?.let { it1 ->
                this.hitListAdapter = HitListAdapter(it1, this)
                binding.recipesRecyclerView.setHasFixedSize(true)
                binding.recipesRecyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recipesRecyclerView.adapter = this.hitListAdapter
                (binding.recipesRecyclerView.adapter as HitListAdapter?)?.notifyDataSetChanged()
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
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHitDetailFragment(item))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // ****ViewModel s7: The View must inform the viewModel if the user entered new information, like this search. The ViewModel must have
        // methods to manage this and update the MutableLiveData so the UI can get the updates in the Observer
        viewModel.searchHits(query.orEmpty())
        binding.root.requestFocus() // To avoid keeping the focus in the SearchView after submit
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}