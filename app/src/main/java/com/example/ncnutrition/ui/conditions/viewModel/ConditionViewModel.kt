package com.example.ncnutrition.ui.conditions.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.model.Condition
import kotlinx.coroutines.launch

class ConditionViewModel(private val conditionDAO: ConditionDAO) : ViewModel() {
    val allConditions:LiveData<List<Condition>> = conditionDAO.getConditions().asLiveData()

    private fun insertCondition(condition: Condition) {
        viewModelScope.launch {
            conditionDAO.insert(condition)
        }
    }

    private fun getNewConditionEntry(
        name: String,
        description: String,
        nutrients: String
    ): Condition {
        return Condition(
            name = name,
            description = description,
            nutrients = nutrients
        )
    }

    fun addNewCondition(
        name: String,
        description: String,
        nutrients: String
    ) {
        val newCondition = getNewConditionEntry(
            name,
            description,
            nutrients
        )
        insertCondition(newCondition)
    }
    fun isEntryValid(name: String,description: String,nutrients: String):Boolean{
        if (name.isBlank()||description.isBlank()||nutrients.isBlank()){
            return false
        }
        return true
    }
}

class ConditionViewModelFactory(private val conditionDAO: ConditionDAO) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConditionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConditionViewModel(conditionDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}