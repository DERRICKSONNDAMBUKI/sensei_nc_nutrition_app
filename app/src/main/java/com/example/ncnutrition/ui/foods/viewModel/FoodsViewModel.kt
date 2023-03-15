package com.example.ncnutrition.ui.foods.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ncnutrition.data.FoodDAO
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.launch

class FoodsViewModel(private val foodDao: FoodDAO) : ViewModel() {
    private fun insertFood(food: Food) {
        viewModelScope.launch {
            foodDao.insert(food)
        }
    }

    private fun getNewFoodEntry(
        food_group_code: Int,
        food_group: String,
        code: String,
        food_name: String,
        edible_conversion_factor: String,
        energy_in_kJ: Double,
        energy_in_kcal: Double,
        water_in_g: Double,
        protein_in_g: Double,
        fat_in_g: Double,
        carbohydrate_available_in_g: Double,
        fibre_in_g: Double,
        ash_in_g: Double,
        ca_in_mg: Double,
        fe_in_mg: Double,
        mg_in_mg: Double,
        p_in_mg: Double,
        k_in_mg: Double,
        na_in_mg: Double,
        zn_in_mg: Double,
        se_in_mg: Double,
        vit_a_rae_in_mcg: Double,
        vit_a_re_in_mcg: Double,
        retinol_in_mcg: Double,
        beta_carotene_equivalent_in_mcg: Double,
        thiamin_in_mcg: Double,
        riboflavin_in_mcg: Double,
        niacin_in_mcg: Double,
        dietary_folate_eq_in_mcg: Double,
        food_folate_in_mcg: Double,
        vit_b12_mcg: Double,
        vit_c_in_mcg: Double,
        cholesterol_mg: Double,
        dish_group_code: Int,
        dish_group_name: String,
        food_description: String,
        food_ingredients: String,
        food_preparation_cooking_serves_makes: String,
        dish_time: String
    ): Food {
        return Food(
            food_group_code = food_group_code,
            food_group = food_group,
            code = code,
            food_name = food_name,
            edible_conversion_factor = edible_conversion_factor,
            energy_in_kJ = energy_in_kJ,
            energy_in_kcal = energy_in_kcal,
            water_in_g = water_in_g,
            protein_in_g = protein_in_g,
            fat_in_g = fat_in_g,
            carbohydrate_available_in_g = carbohydrate_available_in_g,
            fibre_in_g = fibre_in_g,
            ash_in_g = ash_in_g,
            ca_in_mg = ca_in_mg,
            fe_in_mg = fe_in_mg,
            mg_in_mg = mg_in_mg,
            p_in_mg = p_in_mg,
            k_in_mg = k_in_mg,
            na_in_mg = na_in_mg,
            zn_in_mg = zn_in_mg,
            se_in_mg = se_in_mg,
            vit_a_rae_in_mcg = vit_a_rae_in_mcg,
            vit_a_re_in_mcg = vit_a_re_in_mcg,
            retinol_in_mcg = retinol_in_mcg,
            beta_carotene_equivalent_in_mcg = beta_carotene_equivalent_in_mcg,
            thiamin_in_mcg = thiamin_in_mcg,
            riboflavin_in_mcg = riboflavin_in_mcg,
            niacin_in_mcg = niacin_in_mcg,
            dietary_folate_eq_in_mcg = dietary_folate_eq_in_mcg,
            food_folate_in_mcg = food_folate_in_mcg,
            vit_b12_mcg = vit_b12_mcg,
            vit_c_in_mcg = vit_c_in_mcg,
            cholesterol_mg = cholesterol_mg,
            dish_group_code = dish_group_code,
            dish_group_name = dish_group_name,
            food_description = food_description,
            food_ingredients = food_ingredients,
            food_preparation_cooking_serves_makes = food_preparation_cooking_serves_makes,
            dish_time = dish_time
        )
    }
    fun addNewFood(
        food_group_code: Int,
        food_group: String,
        code: String,
        food_name: String,
        edible_conversion_factor: String,
        energy_in_kJ: Double,
        energy_in_kcal: Double,
        water_in_g: Double,
        protein_in_g: Double,
        fat_in_g: Double,
        carbohydrate_available_in_g: Double,
        fibre_in_g: Double,
        ash_in_g: Double,
        ca_in_mg: Double,
        fe_in_mg: Double,
        mg_in_mg: Double,
        p_in_mg: Double,
        k_in_mg: Double,
        na_in_mg: Double,
        zn_in_mg: Double,
        se_in_mg: Double,
        vit_a_rae_in_mcg: Double,
        vit_a_re_in_mcg: Double,
        retinol_in_mcg: Double,
        beta_carotene_equivalent_in_mcg: Double,
        thiamin_in_mcg: Double,
        riboflavin_in_mcg: Double,
        niacin_in_mcg: Double,
        dietary_folate_eq_in_mcg: Double,
        food_folate_in_mcg: Double,
        vit_b12_mcg: Double,
        vit_c_in_mcg: Double,
        cholesterol_mg: Double,
        dish_group_code: Int,
        dish_group_name: String,
        food_description: String,
        food_ingredients: String,
        food_preparation_cooking_serves_makes: String,
        dish_time: String
    ){
        val newFood = getNewFoodEntry(
            food_group_code,
            food_group,
            code,
            food_name,
            edible_conversion_factor,
            energy_in_kJ,
            energy_in_kcal,
            water_in_g,
            protein_in_g,
            fat_in_g,
            carbohydrate_available_in_g,
            fibre_in_g,
            ash_in_g,
            ca_in_mg,
            fe_in_mg,
            mg_in_mg,
            p_in_mg,
            k_in_mg,
            na_in_mg,
            zn_in_mg,
            se_in_mg,
            vit_a_rae_in_mcg,
            vit_a_re_in_mcg,
            retinol_in_mcg,
            beta_carotene_equivalent_in_mcg,
            thiamin_in_mcg,
            riboflavin_in_mcg,
            niacin_in_mcg,
            dietary_folate_eq_in_mcg,
            food_folate_in_mcg,
            vit_b12_mcg,
            vit_c_in_mcg,
            cholesterol_mg,
            dish_group_code,
            dish_group_name,
            food_description,
            food_ingredients,
            food_preparation_cooking_serves_makes,
            dish_time
        )
        insertFood(newFood)
    }
}

class FoodsViewModelFactory(private val FoodDao: FoodDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodsViewModel(FoodDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
//        return super.create(modelClass)
    }
}