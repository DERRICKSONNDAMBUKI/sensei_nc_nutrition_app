package com.example.ncnutrition.ui.mealTable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentMealsListBinding
import com.example.ncnutrition.ui.mealTable.adapter.MealsAdapter
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import java.util.*

/**
 * A fragment representing a list of Items.import com.example.ncnutrition.databinding.FragmentMealsListBinding
 */
class MealsFragment : Fragment() {

    private val viewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding: FragmentMealsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealsListBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        val adapter = MealsAdapter(viewModel){ meal ->
            val date : Date = Date(meal.date.time)
//            val action = MealsFragmentDirections.actionNavigationMealsToMealFragment2(meal.date.Dish_time)
//            this.findNavController().navigate(action)
        }
        binding.mealsRecyclerView.adapter = adapter
//        viewModel
        viewModel.allMeals.observe(this.viewLifecycleOwner) { meals ->
            meals.let {
                adapter.submitList(it)
            }
            if (meals.isEmpty()) {
                Toast.makeText(
                    this.context,
                    "no meals, please choose foods to take",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.mealsRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}