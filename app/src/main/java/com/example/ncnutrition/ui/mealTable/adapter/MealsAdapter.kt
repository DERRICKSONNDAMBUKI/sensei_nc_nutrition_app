package com.example.ncnutrition.ui.mealTable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.FragmentMealsBinding
import com.example.ncnutrition.model.Meal

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MealsAdapter(private val onMealClicked: (Meal) -> Unit) :
    ListAdapter<Meal, MealsAdapter.MealViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            FragmentMealsBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onMealClicked(current)
        }
        holder.bind(current)
    }

    inner class MealViewHolder(private var binding: FragmentMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.apply {
//                 set meal to list

            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}