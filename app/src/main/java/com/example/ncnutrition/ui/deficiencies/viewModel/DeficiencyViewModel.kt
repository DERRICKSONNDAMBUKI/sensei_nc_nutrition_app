package com.example.ncnutrition.ui.deficiencies.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
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

    private fun updateDeficiency(deficiency: Deficiency) {
        viewModelScope.launch {
            deficiencyDAO.update(deficiency)
        }
    }

    fun retrieveDeficiency(id: Int): LiveData<Deficiency> {

        viewModelScope.launch {
            val foods = getFoodsByNutrients("energy_in_kcal", "low")
            val oldDeficiency = deficiencyDAO.getDeficiency(id).toList()
            oldDeficiency.map {
                updateDeficiency(
                    Deficiency(
                        id = it.id,
                        name = it.name,
                        sign_and_symptoms = it.sign_and_symptoms,
                        nutrients = it.nutrients,
                        function = it.function,
                        foods = foods
                    )
                )
            }


        }

        return deficiencyDAO.getDeficiency(id).asLiveData()
    }

    // percentile
    private suspend fun getPercentile(data: Flow<List<Double>>, percentile: Double): Double {

        val maxValue = data.toList().maxOf {
            it.max()
        }
        return (percentile / 100).times(maxValue)
    }

    private suspend fun getFoodsByNutrients(nutrient: String, level: String): List<Food> {
        val nutrientValues = foodDAO.getNutrient(nutrient)


//        percentiles

        val q1 = getPercentile(nutrientValues, 25.0)
        val q2 = getPercentile(nutrientValues, 50.0)
        val q3 = getPercentile(nutrientValues, 75.0)
        return (when (level) {
            "high" -> foodDAO.getHighNutrientFoods(nutrient, q2) as List<Food>
            "low" -> foodDAO.getLowNutrientFoods(nutrient, q2) as List<Food>
            //                 "regular" -> foodDAO.getRegularNutrientFoods(nutrient, q1, q3) as List<Food>
            else -> foodDAO.getFoods() as List<Food>
        })

    }

}

class DeficiencyViewModelFactory(private val deficiencyDAO: DeficiencyDAO, val foodDAO: FoodDAO) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeficiencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return DeficiencyViewModel(deficiencyDAO, foodDAO) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}