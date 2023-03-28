package com.example.ncnutrition.ui.foods.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentFoodBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FoodFragment : Fragment() {
    private val viewModel: FoodViewModel by activityViewModels {
        FoodViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private val mealViewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    lateinit var food: Food


    private fun bind(food: Food) {
        val date = Date()
        binding.apply {
            foodName.text = food.food_name
            calendarViewMealDate.minDate = date.time
        }
    }

    private val navigationArgs: FoodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val code = navigationArgs.code
        viewModel.retrieveFood(code).observe(this.viewLifecycleOwner) { selectedFood ->
            food = selectedFood
            bind(food)
        }
        binding.takeButton.setOnClickListener {
            addNewCondition()
        }
    }

    private fun isEntryValid(): Boolean {
        return mealViewModel.isEntryValid(
            name = binding.editTextMealName.text.toString(),
            date = Date(binding.calendarViewMealDate.date),
            food = food,
        )
    }

    private fun addNewCondition() {
        if (isEntryValid()) {
            val name = binding.editTextMealName.text.toString()
            val date = Date(binding.calendarViewMealDate.date)
            val food = food
            mealViewModel.addNewMeal(
                name = name,
                date = date,
                food = food,
            )
            Toast.makeText(context, "a valid meal $date.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "not a valid meal", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun formattedDate(date: android.icu.util.Calendar): String {
    val dateFormat: DateFormat = SimpleDateFormat("dd-mm-yyy HH:mm:ss")
    val formattedDate = dateFormat.format(date.time)
    return formattedDate
}