package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.databinding.IngredientListItemBinding
import com.juanpoveda.recipes.model.Ingredient

// ****RecyclerViewDiffUtil s4: Change the signature of the adapter class to extend ListAdapter instead RecyclerView.Adapter.
// We need to pass the DiffCallback as a param. ListAdapter keeps track of the list for you and notifies the adapter when the list is updated.
class IngredientListAdapter(listener: OnIngredientClickListener) : ListAdapter<Ingredient, IngredientListAdapter.IngredientsViewHolder>(IngredientListDiffCallback()) {
    private val ingredientClickListener: OnIngredientClickListener = listener
    // ****RecyclerViewDiffUtil s5: Remove the variable that stores the list. It was: private val ingredientList: List<Ingredient> = items

    inner class IngredientsViewHolder(val binding: IngredientListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding = IngredientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {

        with(holder) {
            // ****RecyclerViewDiffUtil s6: As we don't have the variable that stores the list, we can use getItem(position)
            // to get the item in onBindViewHolder. It was: with(ingredientList[position]) and now:
            with(getItem(position)) {
                binding.ingredientTitleTextView.text = this.customText
                Glide.with(binding.root).load(this.image).into(binding.ingredientImageView)
                binding.root.setOnClickListener {
                    ingredientClickListener.onIngredientClick(this)
                }
            }
        }

    }

    // ****RecyclerViewDiffUtil s7: Remove getItemCount() method. It's not required by ListAdapter. It was: getItemCount() = ingredientList.size

    interface OnIngredientClickListener {
        fun onIngredientClick(item: Ingredient)
    }
}

// ****RecyclerViewDiffUtil s1: Create a DiffCallback class in the same file that the Adapter, in this case the adapter is for items
// of type Ingredient so we extend DiffUtil.ItemCallback<Ingredient>(). Implement the methods that are required
class IngredientListDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
    // ****RecyclerViewDiffUtil s2: Write the code to test if 2 items are the same item. If the items have the same text, they are the same item,
    // so return true. Otherwise, return false
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.text.equals(newItem.text, true)
    }
    // ****RecyclerViewDiffUtil s3: Write the code to check if the content of 2 items is the same. As we are using dataClass, then we can compare with ==.
    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }
}