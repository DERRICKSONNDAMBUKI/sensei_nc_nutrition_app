package com.example.ncnutrition.ui.conditions.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class ConditionViewModel(private val conditionDAO: ConditionDAO, private val foodDAO: FoodDAO) :
    ViewModel() {

    val allConditions: LiveData<List<Condition>> = conditionDAO.getConditions().asLiveData()
    val allFoods: LiveData<List<Food>> = foodDAO.getRichEnergyFoods(450.0,550.0).asLiveData()

    private fun insertCondition(condition: Condition) {
        viewModelScope.launch {
            conditionDAO.insert(condition)
        }
    }

    private fun getNewConditionEntry(
        name: String, description: String, nutrients: String
    ): Condition {
        return Condition(
            name = name, description = description, nutrients = nutrients
        )
    }

    fun addNewCondition(
        name: String, description: String, nutrients: String
    ) {
        val newCondition = getNewConditionEntry(
            name, description, nutrients
        )
        insertCondition(newCondition)
    }

    fun isEntryValid(name: String, description: String, nutrients: String): Boolean {
        if (name.isBlank() || description.isBlank() || nutrients.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveCondition(id: Int): LiveData<Condition> {
        return conditionDAO.getCondition(id).asLiveData()
    }

    // percentile
    private suspend fun getPercentile(data: Flow<List<Double>>, percentile: Double): Double {

        val maxValue = data.toList().maxOf {
            it.max()
        }
        return (percentile / 100).times(maxValue)
    }
}


class ConditionViewModelFactory(
    private val conditionDAO: ConditionDAO,
    private val foodDAO: FoodDAO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConditionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ConditionViewModel(conditionDAO, foodDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}