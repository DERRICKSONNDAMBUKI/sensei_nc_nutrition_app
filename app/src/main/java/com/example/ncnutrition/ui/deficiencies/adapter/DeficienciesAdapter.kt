package com.example.ncnutrition.ui.deficiencies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.FragmentDeficienciesBinding
import com.example.ncnutrition.model.Deficiency


class DeficienciesAdapter(
    private val onDeficiencyClicked: (Deficiency) -> Unit
) : ListAdapter<Deficiency, DeficienciesAdapter.DeficiencyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeficiencyViewHolder {

        return DeficiencyViewHolder(
            FragmentDeficienciesBinding.inflate(
                LayoutInflater.from(parent.context),
            )
        )
    }

    override fun onBindViewHolder(holder: DeficiencyViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onDeficiencyClicked(current)
        }
        holder.bind(current)
    }

    inner class DeficiencyViewHolder(private var binding: FragmentDeficienciesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(deficiency: Deficiency) {
            binding.apply {
                deficienciesId.text = deficiency.id.toString()
                deficienciesName.text = deficiency.name
                deficienciesSignAndSymptoms.text = deficiency.sign_and_symptoms
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Deficiency>() {
            override fun areItemsTheSame(oldItem: Deficiency, newItem: Deficiency): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Deficiency, newItem: Deficiency): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}