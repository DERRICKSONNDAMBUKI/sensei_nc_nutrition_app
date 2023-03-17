package com.example.ncnutrition.ui.conditions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.ConditionsItemBinding
import com.example.ncnutrition.model.Condition


class ConditionsAdapter(
    private val onConditionClicked: (Condition) -> Unit
) : ListAdapter<Condition, ConditionsAdapter.ConditionViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionViewHolder {
        return ConditionViewHolder(
            ConditionsItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    }

    override fun onBindViewHolder(holder: ConditionViewHolder, position: Int) {
      val current = getItem(position)
        holder.itemView.setOnClickListener {
            onConditionClicked(current)
        }
        holder.bind(current)
    }



    inner class ConditionViewHolder(private var binding: ConditionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
       fun bind(condition: Condition){
           binding.apply {
               itemNumber.text = condition.id.toString()
               content.text = condition.name
           }
       }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Condition>() {
            override fun areItemsTheSame(oldItem: Condition, newItem: Condition): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Condition, newItem: Condition): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}