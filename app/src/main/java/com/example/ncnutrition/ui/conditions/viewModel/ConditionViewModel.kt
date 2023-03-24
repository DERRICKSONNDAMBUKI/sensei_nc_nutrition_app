package com.example.ncnutrition.ui.conditions.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ConditionViewModel(private val conditionDAO: ConditionDAO, private val foodDAO: FoodDAO) :
    ViewModel() {

    val allConditions: LiveData<List<Condition>> = conditionDAO.getConditions().asLiveData()
//    val allFoods: LiveData<List<Food>>  =  conditionFoods("Cancer")

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


    fun getConditionFoods(id: Int):LiveData<List<Food>>{
        val conditionFlow = conditionDAO.getCondition(id)
        val condition = runBlocking {
            conditionFlow.first()
        }
        return conditionFoods(condition)
    }

    private fun conditionFoods(condition: Condition): LiveData<List<Food>> {
        val liveDataFoodList = when (condition.name) {
            "Cancer" ->  joinLiveDataLists(getFoodsByNutrients("energy_in_kcal", "low").asLiveData(), getFoodsByNutrients("energy_in_kcal", "low").asLiveData())

            else -> joinLiveDataLists(getFoodsByNutrients("energy_in_kcal", "low").asLiveData())
        }
        return liveDataFoodList
    }
    private fun joinLiveDataLists(vararg liveDataList:LiveData<List<Food>>):LiveData<List<Food>>{
        val resultLiveData = MediatorLiveData<List<Food>>()
        val dataList = mutableListOf<Food>()
        for (liveData in liveDataList){
            resultLiveData.addSource(liveData){ foodList->
                dataList.addAll(foodList)
                resultLiveData.value = dataList
            }
        }
        return resultLiveData
    }

    // percentile
    private fun getPercentile(data: List<Double>, percentile: Double): Double {
        val maxValue = data.max()
        return maxValue.times(percentile / 100)
    }

    private fun getFoodsByNutrients(nutrient: String, level: String): Flow<List<Food>> {

        val nutrientValues = foodDAO.getEnergyInKcal()
        val nutrientValueList:List<Double> = runBlocking {
            nutrientValues.first()
        }
//        percentiles
        val q1 = getPercentile(nutrientValueList, 25.0)
        val q2 = getPercentile(nutrientValueList, 50.0)
        val q3 = getPercentile(nutrientValueList, 75.0)

        return when (nutrient) {
            "energy_in_kcal" -> return (when (level) {
                "high" -> foodDAO.getRichEnergyFoods(q2)
                "low" -> foodDAO.getLowEnergyFoods(q2)
                "regular" -> foodDAO.getRegularEnergyFoods(q1, q3)
                else -> foodDAO.getFoods()
            })
            else -> foodDAO.getFoods()
        }

    }
}


class ConditionViewModelFactory(
    private val conditionDAO: ConditionDAO, private val foodDAO: FoodDAO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConditionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ConditionViewModel(conditionDAO, foodDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}