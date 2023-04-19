package com.example.ncnutrition.ui.search.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Food

class SearchViewModel(private val foodDAO: FoodDAO,private val conditionDAO: ConditionDAO, private val deficiencyDAO: DeficiencyDAO):
ViewModel(){

    fun searchQuery(query: String=""):LiveData<List<Food>>{
        return foodDAO.findFoodByName("%$query%").asLiveData()
    }

}
class SearchViewModelFactory(
    private val foodDAO: FoodDAO, private val conditionDAO: ConditionDAO,private val deficiencyDAO: DeficiencyDAO
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            @Suppress("UNCHECKED_CAST") return SearchViewModel(
                foodDAO, conditionDAO, deficiencyDAO
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}