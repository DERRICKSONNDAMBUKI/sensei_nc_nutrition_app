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


    fun getConditionFoods(id: Int): LiveData<List<Food>> {
        val conditionFlow = conditionDAO.getCondition(id)
        val condition = runBlocking {
            conditionFlow.first()
        }
        return conditionFoods(condition)
    }

    private fun conditionFoods(condition: Condition): LiveData<List<Food>> {
        val liveDataFoodList = when (condition.name) {
            "Cancer" -> joinLiveDataLists(getFoodsByNutrients("water_in_g", "low").asLiveData())

            else -> emptyFlow<List<Food>>().asLiveData()
        }
        return liveDataFoodList
    }

    private fun joinLiveDataLists(vararg liveDataList: LiveData<List<Food>>): LiveData<List<Food>> {
        val resultLiveData = MediatorLiveData<List<Food>>()
        val dataList = mutableListOf<Food>()
        for (liveData in liveDataList) {
            resultLiveData.addSource(liveData) { foodList ->
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

        val nutrientValues = when (nutrient) {
            "energy_in_kcal" -> runBlocking { foodDAO.getEnergyInKcal().first() }
            "water_in_g" -> runBlocking { foodDAO.getWaterInG().first() }
            "protein_in_g" -> runBlocking { foodDAO.getProteinInG().first() }
            "fat_in_g" -> runBlocking { foodDAO.getFatInG().first() }
            "carbohydrate_available_in_g" -> runBlocking { foodDAO.getCarbohydrateAvailableInG().first() }
            "fibre_in_g" -> runBlocking { foodDAO.getFibreInG().first() }
            "ash_in_g" -> runBlocking { foodDAO.getAshInG().first() }
            "ca_in_mg" -> runBlocking { foodDAO.getCaInMg().first() }
            "fe_in_mg" -> runBlocking { foodDAO.getFeInMg().first() }
            "mg_in_mg" -> runBlocking { foodDAO.getMgInMg().first() }
            "p_in_mg" -> runBlocking { foodDAO.getPInMg().first() }
            "k_in_mg" -> runBlocking { foodDAO.getKInMg().first() }
            "na_in_mg" -> runBlocking { foodDAO.getNaInMg().first() }
            "zn_in_mg" -> runBlocking { foodDAO.getZnInMg().first() }
            "se_in_mg" -> runBlocking { foodDAO.getWaterInG().first() }
            "vit_a_rae_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "vit_a_re_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "retinol_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "beta_carotene_equivalent_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "thiamin_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "riboflavin_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "niacin_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "dietary_folate_eq_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "food_folate_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "vit_b12_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "vit_c_in_mcg" -> runBlocking { foodDAO.getWaterInG().first() }
            "cholesterol_mg" -> runBlocking { foodDAO.getWaterInG().first() }
            else -> emptyList()
        }

//        val nutrientValueList: List<Double> = runBlocking {
//            nutrientValues.first()
//        }
//        percentiles
        val q1 = getPercentile(nutrientValues, 25.0)
        val q2 = getPercentile(nutrientValues, 50.0)
        val q3 = getPercentile(nutrientValues, 75.0)

        return when (nutrient) {
            "energy_in_kcal" -> return (when (level) {
                "high" -> foodDAO.getRichEnergyFoods(q2)
                "low" -> foodDAO.getLowEnergyFoods(q2)
                "regular" -> foodDAO.getRegularEnergyFoods(q1, q3)
                else -> emptyFlow()
            })
            "water_in_g" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "protein_in_g" -> return (when (level) {
                "high" -> foodDAO.getRichProteinInG(q2)
                "low" -> foodDAO.getLowProteinInG(q2)
                "regular" -> foodDAO.getRegularProteinInG(q1, q3)
                else -> emptyFlow()
            })
            "fat_in_g" -> return (when (level) {
                "high" -> foodDAO.getRichFatInG(q2)
                "low" -> foodDAO.getLowFatInG(q2)
                "regular" -> foodDAO.getRegularFatInG(q1, q3)
                else -> emptyFlow()
            })
            "carbohydrate_available_in_g" -> return (when (level) {
                "high" -> foodDAO.getRichCarbohydrateAvailableInG(q2)
                "low" -> foodDAO.getLowCarbohydrateAvailableInG(q2)
                "regular" -> foodDAO.getRegularCarbohydrateAvailableInG(q1, q3)
                else -> emptyFlow()
            })
            "fibre_in_g" -> return (when (level) {
                "high" -> foodDAO.getRichFibreInG(q2)
                "low" -> foodDAO.getLowFibreInG(q2)
                "regular" -> foodDAO.getRegularFibreInG(q1, q3)
                else -> emptyFlow()
            })
            "ash_in_g" -> return (when (level) {
                "high" -> foodDAO.getRichAshInG(q2)
                "low" -> foodDAO.getLowAshInG(q2)
                "regular" -> foodDAO.getRegularAshInG(q1, q3)
                else -> emptyFlow()
            })
            "ca_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichCaInMg(q2)
                "low" -> foodDAO.getLowCaInMg(q2)
                "regular" -> foodDAO.getRegularCaInMg(q1, q3)
                else -> emptyFlow()
            })
            "fe_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichFeInMg(q2)
                "low" -> foodDAO.getLowFeInMg(q2)
                "regular" -> foodDAO.getRegularFeInMg(q1, q3)
                else -> emptyFlow()
            })
            "mg_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichMgInMg(q2)
                "low" -> foodDAO.getLowMgInMg(q2)
                "regular" -> foodDAO.getRegularMgInMg(q1, q3)
                else -> emptyFlow()
            })
            "p_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichPInMg(q2)
                "low" -> foodDAO.getLowPInMg(q2)
                "regular" -> foodDAO.getRegularPInMg(q1, q3)
                else -> emptyFlow()
            })
            "k_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichKInMg(q2)
                "low" -> foodDAO.getLowKInMg(q2)
                "regular" -> foodDAO.getRegularKInMg(q1, q3)
                else -> emptyFlow()
            })
            "na_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichNaInMg(q2)
                "low" -> foodDAO.getLowNaInMg(q2)
                "regular" -> foodDAO.getRegularNaInMg(q1, q3)
                else -> emptyFlow()
            })
            "zn_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichZnInMg(q2)
                "low" -> foodDAO.getLowZnInMg(q2)
                "regular" -> foodDAO.getRegularZnInMg(q1, q3)
                else -> emptyFlow()
            })
            "se_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "vit_a_rae_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "vit_a_re_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "retinol_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "beta_carotene_equivalent_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "thiamin_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "riboflavin_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "niacin_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "dietary_folate_eq_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "food_folate_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "vit_b12_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "vit_c_in_mcg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "cholesterol_mg" -> return (when (level) {
                "high" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            else -> emptyFlow()
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