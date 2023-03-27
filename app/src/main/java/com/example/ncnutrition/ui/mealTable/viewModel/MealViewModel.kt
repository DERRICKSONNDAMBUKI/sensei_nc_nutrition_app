package com.example.ncnutrition.ui.mealTable.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.MealDAO
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.model.Meal
import kotlinx.coroutines.launch
import java.util.*

class MealViewModel(private val mealDAO: MealDAO) : ViewModel() {
    val allMeals :LiveData<List<Meal>> = mealDAO.getMeals().asLiveData()

    private fun insertFood(meal: Meal) {
        viewModelScope.launch {
            mealDAO.insert(meal)
        }
    }

    private fun getNewMealEntry(
        name: String, date: Date, foods: List<Food>
    ): Meal {
        return Meal(
            name = name,
            date = date,
            foods = foods,
        )
    }

    fun addNewMeal(
        name: String, date: Date, food: Food
    ) {
        val allFoods = mutableSetOf<Food>()
        allFoods.add(food)
        val newMeal = getNewMealEntry(
            name = name, date = date, foods = allFoods.toList()
        )
        insertFood(newMeal)
    }

    fun isEntryValid(name: String,date: Date,food: Food):Boolean{
        if (name.isBlank()||food.code.isBlank()){
            return false
        }
        return true
    }

    fun retrieveMeal(date: Date):LiveData<List<Meal>>{
        return mealDAO.getMeal(date).asLiveData()
    }
}

class MealViewModelFactory(
    private val mealDAO: MealDAO
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MealViewModel(mealDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}