package com.example.ncnutrition.ui.mealTable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentProgressBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import java.util.*

class ProgressFragment : Fragment() {

    private val viewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding :FragmentProgressBinding?=null
    private val binding get() = _binding!!

    private var selectedMealDate: Date = Date()
    lateinit var mealFoods: List<Food>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarViewProgress = binding.calendarViewProgress

        val minDate = Date()
        calendarViewProgress.minDate = minDate.time

        calendarViewProgress.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            selectedMealDate = calendar.time
            selectDate(selectedMealDate)
        }




    }
    private fun selectDate(selectedMealDate: Date) {
        viewModel.getMealBefore(selectedMealDate).observe(this.viewLifecycleOwner){
            mealFoods = it
            if (mealFoods.isEmpty()){
                Toast.makeText(context,"${mealFoods.count()} no meals on $selectedMealDate", Toast.LENGTH_SHORT).show()

            }else{
                binding.textViewProgress.text = mealFoods.toString()
                Toast.makeText(context,"${mealFoods.count()} meals on $selectedMealDate",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}