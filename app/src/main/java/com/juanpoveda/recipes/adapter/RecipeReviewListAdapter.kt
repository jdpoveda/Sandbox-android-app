package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.database.RecipeReview
import com.juanpoveda.recipes.databinding.IngredientListItemBinding
import com.juanpoveda.recipes.databinding.RecipeReviewListItemBinding
import com.juanpoveda.recipes.model.Ingredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ****RecyclerViewHeader s7: Add the constants for the different item types outside the Adapter class
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

// ****RecyclerViewHeader s9: Update the class signature. Now the first argument of the ListAdapter is DataItem and the second param is RecyclerView.ViewHolder.
class RecipeReviewListAdapter(listener: OnRecipeReviewClickListener) : ListAdapter<RecipeReviewListAdapter.DataItem, RecyclerView.ViewHolder>(RecipeReviewListDiffCallback()) {

    private val recipeReviewClickListener: OnRecipeReviewClickListener = listener
    // ****RecyclerViewHeader s19: Add a CoroutineScope to avoid manipulating the list in addHeaderAndSubmitList() in the UI thread.
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    // ****RecyclerViewHeader s10: Update the RecipeReviewViewHolder with the from method to inflate the RecipeReviewListItemBinding
    class RecipeReviewViewHolder(val binding: RecipeReviewListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // ****RecyclerViewHeader s15: Add the bind function for the RecipeReviewViewHolder and set all the values to the views.
        fun bind(item: RecipeReview, clickListener: OnRecipeReviewClickListener) {
            binding.reviewPointsTextView.text = "${item.rating}/5"
            Glide.with(binding.root).load(item.imageUrl).into(binding.reviewImageView)
            binding.root.setOnClickListener {
                clickListener.onRecipeReviewClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): RecipeReviewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeReviewListItemBinding.inflate(layoutInflater, parent, false)
                return RecipeReviewViewHolder(binding)
            }
        }
    }

    // ****RecyclerViewHeader s6: Create the ViewHolder for the header inside the Adapter. Inflate the previously created layout
    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recipe_review_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }

    // ****RecyclerViewHeader s11: Update the onCreateViewHolder method to return a RecyclerView.ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val binding = RecipeReviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //return RecipeReviewViewHolder(binding)
        // ****RecyclerViewHeader s12: Update the content of onCreateViewHolder to return the correct ViewHolder for each item
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> RecipeReviewViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    // ****RecyclerViewHeader s13: Update onBindViewHolder to receive a RecyclerView.ViewHolder as first param
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // ****RecyclerViewHeader s14: Call the bind method of the recipeReviewViewHolder. All the commented code will be moved to the bind method inside
        // RecipeReviewViewHolder in the next step.
        when (holder) {
            is RecipeReviewViewHolder -> {
                val reviewItem = getItem(position) as DataItem.RecipeReviewItem
                holder.bind(reviewItem.recipeReview, recipeReviewClickListener)
            }
        }
        /*
        with(holder) {
            with(getItem(position)) {
                binding.reviewPointsTextView.text = "${this.rating}/5"
                Glide.with(binding.root).load(this.imageUrl).into(binding.reviewImageView)
                binding.root.setOnClickListener {
                    recipeReviewClickListener.onRecipeReviewClick(this)
                }
            }
        } */

    }

    interface OnRecipeReviewClickListener {
        fun onRecipeReviewClick(item: RecipeReview)
    }

    // ****RecyclerViewHeader s17: Add a custom submitList function. It will add the header + the list of recipeReviews:
    fun addHeaderAndSubmitList(list: List<RecipeReview>?) {
        // ****RecyclerViewHeader s20: Use the previously defined scope to manipulate the list, after the list is ready, switch to the Main dispatcher to
        // submit the list.
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.RecipeReviewItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    // ****RecyclerViewHeader s8: Override the getItemViewType method to return the right item type depending on the item
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.RecipeReviewItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    // ****RecyclerViewHeader s1: Add a sealed class in the adapter. A sealed class defines a closed type, which means that all subclasses of DataItem
    // must be defined in this file. As a result, the number of subclasses is known to the compiler. It's not possible for another part of your
    // code to define a new type of DataItem that could break your adapter.
    sealed class DataItem {
        // ****RecyclerViewHeader s3: Add the id property because DiffUtil needs it to know whether and how a item has changed.
        abstract val id: Long

        // ****RecyclerViewHeader s2: Add one data class for each type of item that you'll have in the RecyclerView. In this scenario, we have a data
        // class for the RecipeReview items and one object for Header because header will not contain any data (its only attached to a pre-defined layout)
        data class RecipeReviewItem(val recipeReview: RecipeReview): DataItem() {
            // ****RecyclerViewHeader s4: Override the value of id for each item type. For header we use Long.MIN_VALUE to avoid any conflicts with other item id's.
            override val id = recipeReview.id
        }

        object Header: DataItem() {
            override val id = Long.MIN_VALUE
        }
    }
}

// ****RecyclerViewHeader s16: Edit the DiffCallback and its methods to receive a DataItem
class RecipeReviewListDiffCallback : DiffUtil.ItemCallback<RecipeReviewListAdapter.DataItem>() {

    override fun areItemsTheSame(oldItem: RecipeReviewListAdapter.DataItem, newItem: RecipeReviewListAdapter.DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeReviewListAdapter.DataItem, newItem: RecipeReviewListAdapter.DataItem): Boolean {
        return oldItem == newItem
    }
}