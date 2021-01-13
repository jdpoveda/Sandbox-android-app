package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.database.RecipeReview
import com.juanpoveda.recipes.databinding.IngredientListItemBinding
import com.juanpoveda.recipes.databinding.RecipeReviewListItemBinding

class RecipeReviewListAdapter(private val items: List<RecipeReview>, listener: OnRecipeReviewClickListener) : RecyclerView.Adapter<RecipeReviewListAdapter.RecipeReviewViewHolder>() {

    private val recipeReviewClickListener: OnRecipeReviewClickListener = listener
    private val recipeReviewList: List<RecipeReview> = items

    inner class RecipeReviewViewHolder(val binding: RecipeReviewListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeReviewViewHolder {
        val binding = RecipeReviewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeReviewViewHolder, position: Int) {

        with(holder) {
            with(recipeReviewList[position]) {
                binding.reviewPointsTextView.text = "${this.rating}/5"
                Glide.with(binding.root).load(this.imageUrl).into(binding.reviewImageView)
                binding.root.setOnClickListener {
                    recipeReviewClickListener.onRecipeReviewClick(this)
                }
            }
        }

    }

    override fun getItemCount() = recipeReviewList.size

    interface OnRecipeReviewClickListener {
        fun onRecipeReviewClick(item: RecipeReview)
    }
}