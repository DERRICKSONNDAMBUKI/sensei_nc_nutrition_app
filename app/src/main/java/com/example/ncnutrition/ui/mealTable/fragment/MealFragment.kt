package com.example.ncnutrition.ui.mealTable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentMealBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.foods.adapter.FoodsAdapter
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import java.util.*

class MealFragment : Fragment() {

    private val viewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: MealFragmentArgs by navArgs()
    lateinit var mealFoods: List<Food>


    private var selectedMealDate: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarViewMeal = binding.calendarViewMeal
        val recyclerViewMealFoods = binding.recyclerViewMealFoods

        val minDate = Date()
        calendarViewMeal.minDate = minDate.time

        val date = navigationArgs.date
        selectedMealDate = Date(date)
        calendarViewMeal.date = date
        val adapter = FoodsAdapter { food ->
//            to recipe if it's a dish
        }
        recyclerViewMealFoods.adapter = adapter

        calendarViewMeal.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            selectedMealDate = calendar.time
        }

        viewModel.selectedMeal(selectedMealDate).observe(this.viewLifecycleOwner) {
            mealFoods = it
            if (mealFoods.isEmpty()){
                Toast.makeText(context,"no meals on $selectedMealDate",Toast.LENGTH_SHORT).show()
            }else{
                mealFoods.let {
                    adapter.submitList(it)
                    Toast.makeText(context,"${mealFoods.count()} foods on $selectedMealDate",Toast.LENGTH_SHORT).show()
                }
            }

        }
        recyclerViewMealFoods.layoutManager = LinearLayoutManager(this.context)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}