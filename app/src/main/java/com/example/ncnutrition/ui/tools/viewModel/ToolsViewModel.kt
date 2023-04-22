package com.example.ncnutrition.ui.tools.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ncnutrition.data.dao.FoodDAO
import kotlin.math.pow

class ToolsViewModel(private val foodDAO: FoodDAO) : ViewModel() {

    fun getBMI(weight: Double, height: Double): String {

        val category: String = when (weight / (height / 100.0).pow(2.0)) {
            in 0.0..16.0 -> "Underweight severe thinness 😒"
            in 16.0..16.99 -> "Underweight moderate thinness 😒"
            in 17.0..18.49 -> "Underweight mild thinness 😒"
            in 18.5..24.99 -> "Normal Weight 😍"
            in 25.0..29.9 -> "Overweight or pre-obese 😮"
            in 30.0..34.99 -> "Obese class I 😱"
            in 35.0..39.99 -> "Obese class II 😱"
            else -> "Obese class III 😱"
        }

//
//        if (bmi < 16.0) {
//            category = "Underweight severe thinness 😒"
//        } else if (bmi in 16.0..16.99) {
//            category = "Underweight moderate thinness 😒"
//        } else if (bmi in 17.0..18.49) {
//            category = "Underweight mild thinness 😒";
//        } else if (bmi in 18.5..24.99) {
//            category = "Normal Weight 😍";
//        } else if (bmi in 25.0..29.9) {
//            category = "Overweight or pre-obese 😮";
//        } else if (bmi in 30.0..34.99) {
//            category = "Obese class I 😱";
//        } else if (bmi in 35.0..39.99) {
//            category = "Obese class II 😱";
//        } else if (bmi >= 40.0) {
//            category = "Obese class III 😱";
//        } else {
//            category = "please input weight and height"
//        }

        return category
    }

    fun isEntryValid(weight: Double?, height: Double?): Boolean {
        if (weight?.isNaN() != false || weight.toString().isEmpty() || weight.toString().isBlank()
            ||
            height?.isNaN() != false || height.toString().isBlank() || height.toString().isEmpty()
        ) {
            return false
        }
        return true
    }


}

class ToolsViewModelFactory(private val foodDAO: FoodDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToolsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ToolsViewModel(foodDAO) as T
        } else {
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }
}