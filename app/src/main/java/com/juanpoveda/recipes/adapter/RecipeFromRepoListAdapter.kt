package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.databinding.RecipeFromRepoListItemBinding
import com.juanpoveda.recipes.data.domain.RecipeDomain

class RecipeFromRepoListAdapter(listener: OnRecipeFromRepoClickListener) : ListAdapter<RecipeDomain, RecipeFromRepoListAdapter.RecipeFromRepoViewHolder>(RecipeFromRepoListDiffCallback()) {
    private val recipeFromRepoClickListener: OnRecipeFromRepoClickListener = listener

    inner class RecipeFromRepoViewHolder(val binding: RecipeFromRepoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeFromRepoViewHolder {
        val binding = RecipeFromRepoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeFromRepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeFromRepoViewHolder, position: Int) {

        with(holder) {
            with(getItem(position)) {
                binding.recipeRepoTitleTextView.text = this.label
                Glide.with(binding.root).load(this.image).into(binding.recipeRepoImageView)
                binding.root.setOnClickListener {
                    recipeFromRepoClickListener.onRecipeFromRepoClick(this)
                }
            }
        }

    }

    interface OnRecipeFromRepoClickListener {
        fun onRecipeFromRepoClick(item: RecipeDomain)
    }
}

class RecipeFromRepoListDiffCallback : DiffUtil.ItemCallback<RecipeDomain>() {
    override fun areItemsTheSame(oldItem: RecipeDomain, newItem: RecipeDomain): Boolean {
        return oldItem.url.equals(newItem.url, true)
    }
    override fun areContentsTheSame(oldItem: RecipeDomain, newItem: RecipeDomain): Boolean {
        return oldItem == newItem
    }
}