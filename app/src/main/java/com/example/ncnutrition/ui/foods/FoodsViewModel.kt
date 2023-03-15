package com.example.ncnutrition.ui.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ncnutrition.data.FoodDAO

class FoodsViewModel(private val FoodDao: FoodDAO) : ViewModel()

class FoodsViewModelFactory(private val FoodDao: FoodDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodsViewModel(FoodDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
//        return super.create(modelClass)
    }
}