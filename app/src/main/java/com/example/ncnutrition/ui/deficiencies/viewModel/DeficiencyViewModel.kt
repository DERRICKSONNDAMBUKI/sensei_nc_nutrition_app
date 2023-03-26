package com.example.ncnutrition.ui.deficiencies.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
//    update deficiency

    fun retrieveDeficiency(id: Int): LiveData<Deficiency> {
        return deficiencyDAO.getDeficiency(id).asLiveData()
    }


    fun getDeficiencyFoods(id: Int): LiveData<List<Food>> {
        val deficiencyFlow = deficiencyDAO.getDeficiency(id)
        val deficiency = runBlocking {
            deficiencyFlow.first()
        }
        return deficiencyFoods(deficiency)
    }

    private fun deficiencyFoods(deficiency: Deficiency): LiveData<List<Food>> {
        val liveDataFoodList = when (deficiency.name) {
            "Beriberi" -> joinLiveDataLists(
                getFoodsByNutrients("thiamin_in_mcg", "rich"),
            )
            "Megaloblastic anemia" -> joinLiveDataLists(
                getFoodsByNutrients("vit_b12_in_mcg", "rich"),
                getFoodsByNutrients("food_folate_in_mcg", "rich")
            )
            "xephthalmia" -> joinLiveDataLists(
                getFoodsByNutrients("vit_a_rae_in_mcg", "rich"),
                getFoodsByNutrients("retinol_in_mcg", "rich")
            )
//            "Microcytic anemia"->joinLiveDataLists(getFoodsByNutrients("pyridoxine","rich")) // vitamin B6
            "Scuvy" -> joinLiveDataLists(
                getFoodsByNutrients("vit_c_in_mcg", "rich"),
                getFoodsByNutrients("vit_c_in_mcg", "regular")
            )
            "Rickets" -> joinLiveDataLists(
                getFoodsByNutrients("protein_in_g", "rich")
            )
            "Pellagra" -> joinLiveDataLists(
                getFoodsByNutrients("niacin_in_mcg", "rich")
            )
            "Anemia and cataracts" -> joinLiveDataLists(
                getFoodsByNutrients("riboflavin_in_mcg", "rich")
            )
            "Crohn’s disease, cystic fibrosis, and certain rare genetic diseases such as abetalipoproteinemia and ataxia" -> joinLiveDataLists(
                getFoodsByNutrients("vit_c_in_mcg", "rich"),
                getFoodsByNutrients("vit_c_in_mcg", "regular")
            )
            "Anaemia" -> joinLiveDataLists(
                getFoodsByNutrients("riboflavin_in_mcg", "rich"),
                getFoodsByNutrients("food_folate_in_mcg", "rich"),
                getFoodsByNutrients("fe_in_mg", "rich"),
                getFoodsByNutrients("vit_b12_in_mcg", "rich") // add vitamin k
            )
            "Cancers" -> joinLiveDataLists(
                getFoodsByNutrients("beta_carotene_equivalent_in_mcg", "rich"),
                getFoodsByNutrients("ca_in_mg", "rich")
            )
            "Osteoporosis" -> joinLiveDataLists(
                getFoodsByNutrients("ca_in_mg", "rich")
            )
            "Goitre" -> joinLiveDataLists(
                getFoodsByNutrients("na_in_mg", "regular"), // not much required
                foodDAO.findFoodByName("Salt, Iodized")

            )
            "Iron-deficiency anemia" -> joinLiveDataLists(
                getFoodsByNutrients("fe_in_mg", "rich"),
            )
            "hypocalcemia or hypokalemia" -> joinLiveDataLists(
                getFoodsByNutrients("mg_in_mg", "rich")
            )
            "Stunted growth" -> joinLiveDataLists(
                getFoodsByNutrients("zn_in_mg", "rich")
            )
            "Hyponatremia (low blood sodium)" -> joinLiveDataLists(
                getFoodsByNutrients("na_in_mg", "regular") // not much required
            )
            "Hypokalemia, cardiac arrhythmias" -> joinLiveDataLists(
                getFoodsByNutrients("k_in_mg", "rich")
            )
            "Dehydration (water deficiency), gastroenteritis in children" -> joinLiveDataLists(
                getFoodsByNutrients("water_in_g", "rich")
            )
            // energy_in_kJ,
//            energy_in_kcal,
//            water_in_g,
//            protein_in_g,
//            fat_in_g,
//            carbohydrate_available_in_g,
//            fibre_in_g,
//            ash_in_g,
//            ca_in_mg,
//            fe_in_mg,
//            mg_in_mg,
//            p_in_mg,
//            k_in_mg,
//            na_in_mg,
//            zn_in_mg,
//            se_in_mg,
//            vit_a_rae_in_mcg,
//            vit_a_re_in_mcg,
//            retinol_in_mcg,
//            beta_carotene_equivalent_in_mcg,
//            thiamin_in_mcg,
//            riboflavin_in_mcg,
//            niacin_in_mcg,
//            dietary_folate_eq_in_mcg,
//            food_folate_in_mcg,
//            vit_b12_in_mcg,
//            vit_c_in_mcg,
//            cholesterol_in_mg,
            else -> emptyFlow<List<Food>>().asLiveData()
        }
        return liveDataFoodList
    }


    private fun joinLiveDataLists(vararg liveDataList: Flow<List<Food>>): LiveData<List<Food>> {
        val resultLiveData = MediatorLiveData<List<Food>>()
        val dataList = mutableListOf<List<Food>>()

//        add each source LiveData to the MediatorLiveData
        for (liveData in liveDataList) {
            resultLiveData.addSource(liveData.asLiveData()) { data ->
                dataList.add(data)
//                check if all LiveData have emitted their values
                if (dataList.size == liveDataList.size) {
//                    combine all lists into a single list of foods
                    val allFoods = mutableSetOf<Food>()
                    for (foodList in dataList) {
                        allFoods.addAll(foodList)
                    }
//                    Set the new value for the result LiveData
                    resultLiveData.value = allFoods.toList()
                }
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
            "carbohydrate_available_in_g" -> runBlocking {
                foodDAO.getCarbohydrateAvailableInG().first()
            }
            "fibre_in_g" -> runBlocking { foodDAO.getFibreInG().first() }
            "ash_in_g" -> runBlocking { foodDAO.getAshInG().first() }
            "ca_in_mg" -> runBlocking { foodDAO.getCaInMg().first() }
            "fe_in_mg" -> runBlocking { foodDAO.getFeInMg().first() }
            "mg_in_mg" -> runBlocking { foodDAO.getMgInMg().first() }
            "p_in_mg" -> runBlocking { foodDAO.getPInMg().first() }
            "k_in_mg" -> runBlocking { foodDAO.getKInMg().first() }
            "na_in_mg" -> runBlocking { foodDAO.getNaInMg().first() }
            "zn_in_mg" -> runBlocking { foodDAO.getZnInMg().first() }
            "se_in_mg" -> runBlocking { foodDAO.getSeInMg().first() }
            "vit_a_rae_in_mcg" -> runBlocking { foodDAO.getVitARaeInMcg().first() }
            "vit_a_re_in_mcg" -> runBlocking { foodDAO.getVitAReInMcg().first() }
            "retinol_in_mcg" -> runBlocking { foodDAO.getRetinolInMcg().first() }
            "beta_carotene_equivalent_in_mcg" -> runBlocking {
                foodDAO.getBetaCaroteneEquivalentInMcg().first()
            }
            "thiamin_in_mcg" -> runBlocking { foodDAO.getThiaminInMcg().first() }
            "riboflavin_in_mcg" -> runBlocking { foodDAO.getRiboflavinInMcg().first() }
            "niacin_in_mcg" -> runBlocking { foodDAO.getNiacinInMcg().first() }
            "dietary_folate_eq_in_mcg" -> runBlocking { foodDAO.getDietaryFolateEqInMcg().first() }
            "food_folate_in_mcg" -> runBlocking { foodDAO.getFoodFolateInMcg().first() }
            "vit_b12_in_mcg" -> runBlocking { foodDAO.getVitB12InMcg().first() }
            "vit_c_in_mcg" -> runBlocking { foodDAO.getVitCInMcg().first() }
            "cholesterol_in_mg" -> runBlocking { foodDAO.getCholesterolInMg().first() }
            else -> emptyList()
        }

//        percentiles
        val q1 = getPercentile(nutrientValues, 25.0)
        val q2 = getPercentile(nutrientValues, 50.0)
        val q3 = getPercentile(nutrientValues, 75.0)

        return when (nutrient) {
            "energy_in_kcal" -> return (when (level) {
                "rich" -> foodDAO.getRichEnergyFoods(q2)
                "low" -> foodDAO.getLowEnergyFoods(q2)
                "regular" -> foodDAO.getRegularEnergyFoods(q1, q3)
                else -> emptyFlow()
            })
            "water_in_g" -> return (when (level) {
                "rich" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularWaterInG(q1, q3)
                else -> emptyFlow()
            })
            "protein_in_g" -> return (when (level) {
                "rich" -> foodDAO.getRichProteinInG(q2)
                "low" -> foodDAO.getLowProteinInG(q2)
                "regular" -> foodDAO.getRegularProteinInG(q1, q3)
                else -> emptyFlow()
            })
            "fat_in_g" -> return (when (level) {
                "rich" -> foodDAO.getRichFatInG(q2)
                "low" -> foodDAO.getLowFatInG(q2)
                "regular" -> foodDAO.getRegularFatInG(q1, q3)
                else -> emptyFlow()
            })
            "carbohydrate_available_in_g" -> return (when (level) {
                "rich" -> foodDAO.getRichCarbohydrateAvailableInG(q2)
                "low" -> foodDAO.getLowCarbohydrateAvailableInG(q2)
                "regular" -> foodDAO.getRegularCarbohydrateAvailableInG(q1, q3)
                else -> emptyFlow()
            })
            "fibre_in_g" -> return (when (level) {
                "rich" -> foodDAO.getRichFibreInG(q2)
                "low" -> foodDAO.getLowFibreInG(q2)
                "regular" -> foodDAO.getRegularFibreInG(q1, q3)
                else -> emptyFlow()
            })
            "ash_in_g" -> return (when (level) {
                "rich" -> foodDAO.getRichAshInG(q2)
                "low" -> foodDAO.getLowAshInG(q2)
                "regular" -> foodDAO.getRegularAshInG(q1, q3)
                else -> emptyFlow()
            })
            "ca_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichCaInMg(q2)
                "low" -> foodDAO.getLowCaInMg(q2)
                "regular" -> foodDAO.getRegularCaInMg(q1, q3)
                else -> emptyFlow()
            })
            "fe_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichFeInMg(q2)
                "low" -> foodDAO.getLowFeInMg(q2)
                "regular" -> foodDAO.getRegularFeInMg(q1, q3)
                else -> emptyFlow()
            })
            "mg_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichMgInMg(q2)
                "low" -> foodDAO.getLowMgInMg(q2)
                "regular" -> foodDAO.getRegularMgInMg(q1, q3)
                else -> emptyFlow()
            })
            "p_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichPInMg(q2)
                "low" -> foodDAO.getLowPInMg(q2)
                "regular" -> foodDAO.getRegularPInMg(q1, q3)
                else -> emptyFlow()
            })
            "k_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichKInMg(q2)
                "low" -> foodDAO.getLowKInMg(q2)
                "regular" -> foodDAO.getRegularKInMg(q1, q3)
                else -> emptyFlow()
            })
            "na_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichNaInMg(q2)
                "low" -> foodDAO.getLowNaInMg(q2)
                "regular" -> foodDAO.getRegularNaInMg(q1, q3)
                else -> emptyFlow()
            })
            "zn_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichZnInMg(q2)
                "low" -> foodDAO.getLowZnInMg(q2)
                "regular" -> foodDAO.getRegularZnInMg(q1, q3)
                else -> emptyFlow()
            })
            "se_in_mg" -> return (when (level) {
                "high" -> foodDAO.getRichSeInMg(q2)
                "low" -> foodDAO.getLowSeInMg(q2)
                "regular" -> foodDAO.getRegularSeInMg(q1, q3)
                else -> emptyFlow()
            })
            "vit_a_rae_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichVitARaeInMcg(q2)
                "low" -> foodDAO.getLowVitARaeInMcg(q2)
                "regular" -> foodDAO.getRegularVitARaeInMcg(q1, q3)
                else -> emptyFlow()
            })
            "vit_a_re_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichVitAReInMcg(q2)
                "low" -> foodDAO.getLowVitAReInMcg(q2)
                "regular" -> foodDAO.getRegularVitAReInMcg(q1, q3)
                else -> emptyFlow()
            })
            "retinol_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichRetinolInMcg(q2)
                "low" -> foodDAO.getLowRetinolInMcg(q2)
                "regular" -> foodDAO.getRegularRetinolInMcg(q1, q3)
                else -> emptyFlow()
            })
            "beta_carotene_equivalent_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichBetaCaroteneEquivalentInMcg(q2)
                "low" -> foodDAO.getLowBetaCaroteneEquivalentInMcg(q2)
                "regular" -> foodDAO.getRegularBetaCaroteneEquivalentInMcg(q1, q3)
                else -> emptyFlow()
            })
            "thiamin_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichThiaminInMcg(q2)
                "low" -> foodDAO.getLowThiaminInMcg(q2)
                "regular" -> foodDAO.getRegularThiaminInMcg(q1, q3)
                else -> emptyFlow()
            })
            "riboflavin_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichRiboflavinInMcg(q2)
                "low" -> foodDAO.getLowRiboflavinInMcg(q2)
                "regular" -> foodDAO.getRegularRiboflavinInMcg(q1, q3)
                else -> emptyFlow()
            })
            "niacin_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichNiacinInMcg(q2)
                "low" -> foodDAO.getLowNiacinInMcg(q2)
                "regular" -> foodDAO.getRegularNiacinInMcg(q1, q3)
                else -> emptyFlow()
            })
            "dietary_folate_eq_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichDietaryFolateEqInMcg(q2)
                "low" -> foodDAO.getLowDietaryFolateEqInMcg(q2)
                "regular" -> foodDAO.getRegularDietaryFolateEqInMcg(q1, q3)
                else -> emptyFlow()
            })
            "food_folate_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichFoodFolateInMcg(q2)
                "low" -> foodDAO.getLowFoodFolateInMcg(q2)
                "regular" -> foodDAO.getRegularFoodFolateInMcg(q1, q3)
                else -> emptyFlow()
            })
            "vit_b12_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichVitB12InMcg(q2)
                "low" -> foodDAO.getLowVitB12InMcg(q2)
                "regular" -> foodDAO.getRegularVitB12InMcg(q1, q3)
                else -> emptyFlow()
            })
            "vit_c_in_mcg" -> return (when (level) {
                "rich" -> foodDAO.getRichWaterInG(q2)
                "low" -> foodDAO.getLowWaterInG(q2)
                "regular" -> foodDAO.getRegularVitCInMcg(q1, q3)
                else -> emptyFlow()
            })
            "cholesterol_in_mg" -> return (when (level) {
                "rich" -> foodDAO.getRichCholesterolInMg(q2)
                "low" -> foodDAO.getLowCholesterolInMg(q2)
                "regular" -> foodDAO.getRegularCholesterolInMg(q1, q3)
                else -> emptyFlow()
            })
            else -> emptyFlow()
        }

    }

}

class DeficiencyViewModelFactory(
    private val deficiencyDAO: DeficiencyDAO, private val foodDAO: FoodDAO
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeficiencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return DeficiencyViewModel(deficiencyDAO, foodDAO) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}