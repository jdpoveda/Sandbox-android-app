package com.juanpoveda.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juanpoveda.recipes.databinding.HitListItemBinding
import com.juanpoveda.recipes.model.Hit

// ****RecyclerView s3: Create the Adapter class for the RecyclerView with the list of items in the constructor. We'll need ViewBinding here also.
class HitListAdapter(private val items: List<Hit>, listener: OnHitClickListener) : RecyclerView.Adapter<HitListAdapter.HitsViewHolder>() {

    // ****RecyclerView s3-3: Add a class field for the listener (the original listener must be passed in the constructor of the Adapter)
    private val hitClickListener: OnHitClickListener = listener
    private val hitList: List<Hit> = items

    // ****RecyclerView s3-4: Create the ViewHolder class. It should receive the Binding of the item view. In this case the item layout is
    // hit_list_item.xml so the binder is HitListItemBinding.

    // ****ViewBindingRecyclerView s2: In the ViewHolder, the constructor must receive the Binding of the item view. In this case the item layout is
    //  hit_list_item.xml so the binder is HitListItemBinding.
    inner class HitsViewHolder(val binding: HitListItemBinding) : RecyclerView.ViewHolder(binding.root)

    // ****RecyclerView s3-5: Create the new views by overriding onCreateViewHolder (invoked by the layout manager). Note that in order to
    // use view binding, the layout must be inflated using the Binding class (HitListItemBinding in this case)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HitsViewHolder {
        // ****ViewBindingRecyclerView s3: Inflate and return the view of the items using the Binding class
        val binding = HitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HitsViewHolder(binding)
    }

    // ****RecyclerView s3-6: Replace the contents of a view by overriding onBindViewHolder (invoked by the layout manager). Here, the data
    // from the item can be assigned to the different Views of the layout and also the clickListeners can be configured to get the events.
    override fun onBindViewHolder(holder: HitsViewHolder, position: Int) {

        with(holder) {
            with(hitList[position]) {
                // ****ViewBindingRecyclerView s4: To access the views of the layout, just use the binding val set in HitsViewHolder:
                binding.hitTitleTextView.text = this.recipe.label
                // ****Glide s2: Load the image url into the desired ImageView
                Glide.with(binding.root).load(this.recipe.image).into(binding.hitImageView)
                binding.root.setOnClickListener {
                    hitClickListener.onHitClick(this)
                }
            }
        }

    }

    // ****RecyclerView s3-1: Return the size of the list (invoked by the layout manager)
    override fun getItemCount() = hitList.size

    // ****RecyclerView s3-2: Add an Interface to listen for the item click events (if needed)
    interface OnHitClickListener {
        fun onHitClick(item: Hit)
    }
}