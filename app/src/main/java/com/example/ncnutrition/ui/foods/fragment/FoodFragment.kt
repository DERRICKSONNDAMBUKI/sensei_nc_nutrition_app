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
import java.time.ZoneId
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
    lateinit var selectedDate:Date


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
        val calendarDate = binding.calendarViewMealDate

        calendarDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Do something with the selected date

            // Create a calendar object and set it to the selected date
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            // Save the selected date to the public variable
            selectedDate = calendar.time
//            Toast.makeText(context,"Selected date $selectedDate",Toast.LENGTH_SHORT).show()
        }
        binding.takeButton.setOnClickListener {
            addNewCondition()
        }
    }

    private fun isEntryValid(): Boolean {

        return mealViewModel.isEntryValid(
            name = binding.editTextMealName.text.toString(),
//            date = Date(binding.calendarViewMealDate.date),
            date = selectedDate,
            food = food,
        )
    }

    private fun addNewCondition() {
        if (isEntryValid()) {
            val name = binding.editTextMealName.text.toString()
            val date = selectedDate
            val food = food
            mealViewModel.addNewMeal(
                name = name,
                date = date,
                food = food,
            )
            Toast.makeText(
                context,
                "saved food for $name on  ${
                    date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth
                }.",
                Toast.LENGTH_SHORT
            ).show()
            binding.editTextMealName.text.clear()


        } else {
            Toast.makeText(context, "invalid meal, e.g breakfast", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}