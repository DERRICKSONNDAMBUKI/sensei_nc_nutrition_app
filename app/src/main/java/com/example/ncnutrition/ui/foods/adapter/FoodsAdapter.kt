package com.example.ncnutrition.ui.foods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.FragmentFoodsBinding
import com.example.ncnutrition.model.Food

class FoodsAdapter(private val onFoodClicked: (Food) -> Unit) :
    ListAdapter<Food, FoodsAdapter.FoodViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            FragmentFoodsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onFoodClicked(current)
        }
        holder.bind(current)
    }

    class FoodViewHolder(private var binding: FragmentFoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
//            set data to fragment
            binding.apply {
                foodsFoodName.text = food.food_name
                foodsFoodGroup.text = food.food_group
                foodsEnergy.text = food.energy_in_kcal.toString()
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