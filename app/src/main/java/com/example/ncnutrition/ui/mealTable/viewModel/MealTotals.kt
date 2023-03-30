package com.example.ncnutrition.ui.mealTable.viewModel

import com.example.ncnutrition.model.Food

class MealTotals(private val foodList: List<Food>) {
    //        energy
    val energyInKJ: Double
        get()= foodList.sumOf { it.energy_in_kJ!! }
    val energyInKcal: Double
        get() = foodList.sumOf { it.energy_in_kcal!! }


    //        proximate
    val waterInG: Double
        get()= foodList.sumOf { it.water_in_g!! }
    val proteinInG: Double
        get() = foodList.sumOf { it.protein_in_g!! }
    val fatInG: Double
        get() = foodList.sumOf { it.fat_in_g!! }
    val carbohydrateAvailableInG: Double
        get() =foodList.sumOf { it.carbohydrate_available_in_g!! }
    val fibreInG: Double
        get() = foodList.sumOf { it.fibre_in_g!! }
    val ashInG: Double
        get() =foodList.sumOf { it.ash_in_g!! }

    //        minerals
    val caInMg: Double
        get() =foodList.sumOf { it.ca_in_mg!! }
    val feInMg: Double
        get() = foodList.sumOf { it.fe_in_mg!! }
    val mgInMg: Double
        get() =foodList.sumOf { it.mg_in_mg!! }
    val pInMg: Double
        get() =foodList.sumOf { it.p_in_mg!! }
    val kInMg: Double
        get() =foodList.sumOf { it.k_in_mg!! }
    val naInMg: Double
        get() =foodList.sumOf { it.na_in_mg!! }
    val znInMg: Double
        get() =foodList.sumOf { it.zn_in_mg!! }
    val seInMg: Double
        get() =foodList.sumOf { it.se_in_mg!! }

    //        vitamins
    val vitARaeInMcg: Double
        get() =foodList.sumOf { it.vit_a_rae_in_mcg!! }
    val vitAReInMcg: Double
        get() = foodList.sumOf { it.vit_a_re_in_mcg!! }
    val retinolInMcg: Double
        get() =foodList.sumOf { it.retinol_in_mcg!! }
    val betaCaroteneEquivalentInMcg: Double
        get() =foodList.sumOf { it.beta_carotene_equivalent_in_mcg!! }
    val thiaminInMcg: Double
        get() =foodList.sumOf { it.thiamin_in_mcg!! }
    val riboflavinInMcg: Double
        get() =foodList.sumOf { it.riboflavin_in_mcg!! }
    val niacinInMcg: Double
        get() = foodList.sumOf { it.niacin_in_mcg!! }
    val dietaryFolateEqInMcg: Double
        get() =foodList.sumOf { it.dietary_folate_eq_in_mcg!! }
    val foodFolateInMcg: Double
        get() = foodList.sumOf { it.food_folate_in_mcg!! }
    val vitB12InMcg: Double
        get() =foodList.sumOf { it.vit_b12_in_mcg!! }
    val vitCInMcg: Double
        get() =foodList.sumOf { it.vit_c_in_mcg!! }

    //        cholesterol
    val cholesterolInMg: Double
        get() = foodList.sumOf { it.cholesterol_in_mg!! }
}