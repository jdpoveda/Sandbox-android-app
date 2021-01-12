package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.databinding.IngredientListItemBinding
import com.juanpoveda.recipes.model.Ingredient

// ****RecyclerView s3: Create the Adapter class for the RecyclerView with the list of items in the constructor. We'll need ViewBinding here also.
class IngredientListAdapter(private val items: List<Ingredient>, listener: OnIngredientClickListener) : RecyclerView.Adapter<IngredientListAdapter.IngredientsViewHolder>() {

    // ****RecyclerView s3-3: Add a class field for the listener (the original listener must be passed in the constructor of the Adapter)
    private val ingredientClickListener: OnIngredientClickListener = listener
    private val ingredientList: List<Ingredient> = items

    // ****RecyclerView s3-4: Create the ViewHolder class. It should receive the Binding of the item view. In this case the item layout is
    // hit_list_item.xml so the binder is HitListItemBinding.

    // ****ViewBindingRecyclerView s2: In the ViewHolder, the constructor must receive the Binding of the item view. In this case the item layout is
    //  hit_list_item.xml so the binder is HitListItemBinding.
    inner class IngredientsViewHolder(val binding: IngredientListItemBinding) : RecyclerView.ViewHolder(binding.root)

    // ****RecyclerView s3-5: Create the new views by overriding onCreateViewHolder (invoked by the layout manager). Note that in order to
    // use view binding, the layout must be inflated using the Binding class (HitListItemBinding in this case)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        // ****ViewBindingRecyclerView s3: Inflate and return the view of the items using the Binding class
        val binding = IngredientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    // ****RecyclerView s3-6: Replace the contents of a view by overriding onBindViewHolder (invoked by the layout manager). Here, the data
    // from the item can be assigned to the different Views of the layout and also the clickListeners can be configured to get the events.
    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {

        with(holder) {
            with(ingredientList[position]) {
                // ****ViewBindingRecyclerView s4: To access the views of the layout, just use the binding val set in HitsViewHolder:
                binding.ingredientTitleTextView.text = this.customText
                // ****Glide s2: Load the image url into the desired ImageView
                Glide.with(binding.root).load(this.image).into(binding.ingredientImageView)
                binding.root.setOnClickListener {
                    ingredientClickListener.onIngredientClick(this)
                }
            }
        }

    }

    // ****RecyclerView s3-1: Return the size of the list (invoked by the layout manager)
    override fun getItemCount() = ingredientList.size

    // ****RecyclerView s3-2: Add an Interface to listen for the item click events (if needed)
    interface OnIngredientClickListener {
        fun onIngredientClick(item: Ingredient)
    }
}