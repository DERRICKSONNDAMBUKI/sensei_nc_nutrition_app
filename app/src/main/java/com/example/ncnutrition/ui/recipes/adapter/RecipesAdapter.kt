package com.example.ncnutrition.ui.recipes.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.R
import com.example.ncnutrition.databinding.FragmentRecipesBinding
import com.example.ncnutrition.model.Food


class RecipesAdapter(
    private val onRecipeClicked: (Food) -> Unit
) : ListAdapter<Food, RecipesAdapter.RecipeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        return RecipeViewHolder(
            FragmentRecipesBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onRecipeClicked(current)
        }
        holder.bind(current)

    }


    inner class RecipeViewHolder(private var binding: FragmentRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Food) {
            val drawableResId = binding.imageViewRecipe.resources?.getIdentifier("_${recipe.code}","drawable",binding.imageViewRecipe.context.packageName)

            val drawable: Drawable? = if (drawableResId == 0 ||drawableResId==null){
                ContextCompat.getDrawable(binding.imageViewRecipe.context, R.drawable.ic_broken_image)
            }else{
                ContextCompat.getDrawable(binding.imageViewRecipe.context,drawableResId)
            }

            binding.apply {
                itemNumber.text = recipe.code
                content.text = recipe.food_name
                imageViewRecipe.setImageDrawable(drawable)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.code == newItem.code
            }
        }
    }

}