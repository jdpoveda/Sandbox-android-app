package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.databinding.IngredientListItemBinding
import com.juanpoveda.recipes.model.Ingredient

class IngredientListAdapter(private val items: List<Ingredient>, listener: OnIngredientClickListener) : RecyclerView.Adapter<IngredientListAdapter.IngredientsViewHolder>() {

    private val ingredientClickListener: OnIngredientClickListener = listener
    private val ingredientList: List<Ingredient> = items

    inner class IngredientsViewHolder(val binding: IngredientListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding = IngredientListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {

        with(holder) {
            with(ingredientList[position]) {
                binding.ingredientTitleTextView.text = this.customText
                Glide.with(binding.root).load(this.image).into(binding.ingredientImageView)
                binding.root.setOnClickListener {
                    ingredientClickListener.onIngredientClick(this)
                }
            }
        }

    }

    override fun getItemCount() = ingredientList.size

    interface OnIngredientClickListener {
        fun onIngredientClick(item: Ingredient)
    }
}