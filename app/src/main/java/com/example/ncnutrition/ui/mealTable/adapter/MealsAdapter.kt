package com.example.ncnutrition.ui.mealTable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.FragmentMealsBinding
import com.example.ncnutrition.model.Meal
import com.example.ncnutrition.ui.mealTable.fragment.MealsFragmentDirections
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import java.time.ZoneId


class MealsAdapter(
    private val viewModel: MealViewModel, private val onMealClicked: (Meal) -> Unit
) : ListAdapter<Meal, MealsAdapter.MealViewHolder>(DiffCallback) {


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
            val day = meal.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfWeek
            val month = meal.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().month
            val date = meal.date.toInstant().atZone(ZoneId.systemDefault()).dayOfMonth

            if (meal.food.dish_group_code != null) {
                binding.buttonRecipe.visibility = View.VISIBLE
            }
            binding.buttonRecipe.setOnClickListener {
                val action =
                    MealsFragmentDirections.actionNavigationMealsToRecipeFragment(meal.food.code)
                Navigation.findNavController(it).navigate(action)
            }

            binding.apply {
//                 set meal to list
                textViewFoodName.text = meal.food.food_name
                textViewMealName.text = meal.name
                textViewMealDate.text = "${date}th $month $day"
                buttonRemoveMeal.setOnClickListener {
                    viewModel.deleteMeal(meal)
                }

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