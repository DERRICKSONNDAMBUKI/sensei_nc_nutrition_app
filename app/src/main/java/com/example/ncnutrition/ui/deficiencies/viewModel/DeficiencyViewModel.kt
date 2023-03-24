package com.example.ncnutrition.ui.deficiencies.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Deficiency
import kotlinx.coroutines.launch

class DeficiencyViewModel(private val deficiencyDAO: DeficiencyDAO, private val foodDAO: FoodDAO) :
    ViewModel() {


    val allDeficiencies: LiveData<List<Deficiency>> = deficiencyDAO.getDeficiencies().asLiveData()

    private fun insertDeficiency(deficiency: Deficiency) {
        viewModelScope.launch {
            deficiencyDAO.insert(deficiency)
        }
    }

    private fun getNewDeficiencyEntry(
        name: String, sign_and_symptoms: String, nutrients: String, function: String
    ): Deficiency {
        return Deficiency(
            name = name,
            sign_and_symptoms = sign_and_symptoms,
            nutrients = nutrients,
            function = function,
            foods = null
        )
    }

    fun addNewDeficiency(
        name: String, sign_and_symptoms: String, nutrients: String, function: String
    ) {
        val newDeficiency = getNewDeficiencyEntry(
            name, sign_and_symptoms, nutrients, function
        )
        insertDeficiency(newDeficiency)
    }

    fun isEntryValid(
        name: String, sign_and_symptoms: String, nutrients: String, function: String
    ): Boolean {
        if (name.isBlank() || sign_and_symptoms.isBlank() || nutrients.isBlank() || function.isBlank()) {
            return false
        }
        return true
    }

    fun updateDeficiency(deficiency: Deficiency) {
        viewModelScope.launch {
//            val foods = getFoodsByNutrients("energy_in_kcal", "low")

//            val newDeficiency = deficiency.copy(foods = foods as List<Food>)
//            deficiencyDAO.update(newDeficiency)
        }
    }

    fun retrieveDeficiency(id: Int): LiveData<Deficiency> {
//        val deficiency = deficiencyDAO.getDeficiency(id)
        return deficiencyDAO.getDeficiency(id).asLiveData()
    }

//    // percentile
//    private suspend fun getPercentile(data: Flow<List<Double>>, percentile: Double): Double {
//
//        val maxValue = data.toList().maxOf {
//            it.max()
//        }
//        return (percentile / 100).times(maxValue)
//    }
//
//    private suspend fun getFoodsByNutrients(nutrient: String, level: String): Flow<List<Food>?> {
//        val nutrientValues = foodDAO.getEnergyInKcal(nutrient)
//
////        percentiles
//        val q1 = getPercentile(nutrientValues, 25.0)
//        val q2 = getPercentile(nutrientValues, 50.0)
//        val q3 = getPercentile(nutrientValues, 75.0)
//        return (when (level) {
//            "high" -> foodDAO.getRichEnergyFoods( q2,q3)
//            "low" -> foodDAO.getLowNutrientFoods(nutrient, q2)
//            //                 "regular" -> foodDAO.getRegularNutrientFoods(nutrient, q1, q3) as List<Food>
//            else -> foodDAO.getFoods()
//        })
//    }
}

class DeficiencyViewModelFactory(private val deficiencyDAO: DeficiencyDAO, private val foodDAO: FoodDAO) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeficiencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return DeficiencyViewModel(deficiencyDAO, foodDAO) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}