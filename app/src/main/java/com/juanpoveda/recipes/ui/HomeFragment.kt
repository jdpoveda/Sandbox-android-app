package com.juanpoveda.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpoveda.recipes.adapter.HitListAdapter
import com.juanpoveda.recipes.databinding.HomeFragmentBinding
import com.juanpoveda.recipes.model.Hit
import com.juanpoveda.recipes.viewmodel.HomeViewModel

// ****MVVM s2: Generate this Fragment (View) and the corresponding ViewModel by right click
// on the package -> New -> Fragment -> Fragment (With ViewModel).
// This action will generate HomeViewModel class also.
class HomeFragment : Fragment(), HitListAdapter.OnHitClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    // ****MVVM s5: create a class val for the viewModel and initialize it by using: viewModel: HomeViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()
    // ****RecyclerView s4: Create a field for the adapter in the Activity/Fragment
    private lateinit var hitListAdapter: HitListAdapter

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

        // ****MVVM s6: Now, the viewModel can be used to observe the LiveData fields and manage the UI according to the changes in the data.
        viewModel.recipeList.observe(viewLifecycleOwner) {
            // ****ViewBindingFragment s4: We can call the views of the layout directly with binding.viewName
            println("******* hits " + it)

            // ****RecyclerView s5: Instantiate the adapter, pass the clickListener implemented in the Fragment and the list of items.
            this.hitListAdapter = HitListAdapter(it.hits, this)
            binding.recipesRecyclerView.setHasFixedSize(true)
            binding.recipesRecyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recipesRecyclerView.adapter = this.hitListAdapter
            (binding.recipesRecyclerView.adapter as HitListAdapter).notifyDataSetChanged()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHitClick(item: Hit) {
        println("******* Hit clicked! " + item)
    }

}