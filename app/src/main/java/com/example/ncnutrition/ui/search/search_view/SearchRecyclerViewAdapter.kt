package com.example.ncnutrition.ui.search.search_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.FragmentSearchBinding
import com.example.ncnutrition.model.Food


class SearchRecyclerViewAdapter(
    private val onSearchClicked: (Food) -> Unit
) : ListAdapter<Food, SearchRecyclerViewAdapter.SearchViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            FragmentSearchBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onSearchClicked(current)
        }
        holder.bind(current)
    }

    inner class SearchViewHolder(private var binding: FragmentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.apply {
                itemNumber.text = food.code
                content.text = food.food_name
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}