package com.example.ncnutrition.ui.mealTable.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.MealDAO
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class MealViewModel(private val mealDAO: MealDAO) : ViewModel() {
    val allMeals: LiveData<List<Meal>> = mealDAO.getMeals().asLiveData()

    fun getMealsBefore(date: Date): LiveData<List<Food>> {
        val mealsBefore = mealDAO.getMealsBefore(date)
        val foodListLiveData = getMealPropertyList(mealsBefore)

        getTotalFoodsNutrients(foodListLiveData.value)
        return foodListLiveData
    }
    fun getEnergyTotals(date:Date){
        val mealsBefore = mealDAO.getMealsBefore(date)
        val foodListLiveData = getMealPropertyList(mealsBefore)
        energyTotals(foodListLiveData.value)
    }

    private fun energyTotals(foodList: List<Food>?) {
        //        energy foodList
        val energyInKJ: Double
        val energyInKcal: Double
    }

    private fun getTotalFoodsNutrients(foodList: List<Food>?) {
//        energy
        val energyInKJ: Double
        val energyInKcal: Double
               
//        proximate
        val waterInG: Double
        val proteinInG: Double
        val fatInG: Double
        val carbohydrateAvailableInG: Double
        val fibreInG: Double
        val ashInG: Double

//        minerals
        val caInMg: Double
        val feInMg: Double
        val mgInMg: Double
        val pInMg: Double
        val kInMg: Double
        val naInMg: Double
        val znInMg: Double
        val seInMg: Double

//        vitamins
        val vitARaeInMcg: Double
        val vitAReInMcg: Double
        val retinolInMcg: Double
        val betaCaroteneEquivalentInMcg: Double
        val thiaminInMcg: Double
        val riboflavinInMcg: Double
        val niacinInMcg: Double
        val dietaryFolateEqInMcg: Double
        val foodFolateInMcg: Double
        val vitB12InMcg: Double
        val vitCInMcg: Double

//        cholesterol
        val cholesterolInMg: Double
    }

    fun selectedMeal(date: Date): LiveData<List<Food>> {
        val allMealsForDate = allMeals.value
        val mealsOnDate = mealDAO.getMeal(date)
        Log.i("meals", mealsOnDate.toString())
        return getMealPropertyList(mealsOnDate)

    }

    private fun getMealPropertyList(mealsFlow: Flow<List<Meal>>): LiveData<List<Food>> {
        return mealsFlow.flatMapLatest { meals ->
            flow {
                val foodList = mutableListOf<Food>()
                meals.forEach {
                    foodList.add(it.food)

                }
                emit(foodList.toList())
            }
        }.asLiveData()
    }


    private fun joinFoods(meals: LiveData<List<Meal>>): LiveData<List<Food>> {
        val foods = mutableListOf<Food>()

        meals.value?.forEach {
            val mealFoods = mutableListOf<Food>()
            mealFoods.add(it.food)
        }

        return MutableLiveData(foods.toList())
    }

    private fun insertFood(meal: Meal) {
        viewModelScope.launch {
            mealDAO.insert(meal)
        }
    }

    private fun getNewMealEntry(
        name: String, date: Date, food: Food
//        foods: List<Food>
    ): Meal {
        return Meal(
            name = name, date = date,
//            foods = foods,
            food = food
        )
    }

    fun addNewMeal(
        name: String, date: Date, food: Food
    ) {
//        val allFoods = mutableSetOf<Food>()
//        allFoods.add(food)
        val newMeal = getNewMealEntry(
            name = name, date = date,
//            foods = allFoods.toList()
            food = food
        )
        insertFood(newMeal)
    }

    fun isEntryValid(name: String, date: Date, food: Food): Boolean {
        if (name.isBlank() || food.code.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveMeal(date: Date): LiveData<List<Meal>> {
        return mealDAO.getMeal(date).asLiveData()
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDAO.delete(meal)
        }
    }
}

class MealViewModelFactory(
    private val mealDAO: MealDAO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MealViewModel(mealDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}