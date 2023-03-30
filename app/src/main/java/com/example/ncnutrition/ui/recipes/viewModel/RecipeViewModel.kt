package com.example.ncnutrition.ui.recipes.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Food

class RecipeViewModel(private val foodDAO: FoodDAO):ViewModel(){
    fun allRecipes ():LiveData<List<Food>> {
        val foodLisLiveData =foodDAO.getFoodsByFoodGroup(15).asLiveData()
        foodLisLiveData.value?.filter {
            it.dish_group_code != null
        }
       return foodLisLiveData
    }

//    dish group foods
    fun getFoodsByDishGroup(  dish_group_code:Int): LiveData<List<Food>> {

        return foodDAO.getFoodsByDishGroup(dish_group_code ).asLiveData()
    }

    fun retrieveRecipe(code: String): LiveData<Food> {
        return foodDAO.getFood(code).asLiveData()
    }

}

class RecipeViewModelFactory(
    private val foodDAO: FoodDAO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return RecipeViewModel(foodDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}