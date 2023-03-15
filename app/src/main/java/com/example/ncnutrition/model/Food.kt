package com.example.ncnutrition.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Food_group_code")
    val food_group_code: Int,
    @ColumnInfo(name = "Food_group")
    val food_group: String,
    @ColumnInfo(name = "Code")
    val code: String,
    @ColumnInfo(name = "Food_name")
    val food_name: String,
    @ColumnInfo(name = "Edible_conversion_factor")
    val edible_conversion_factor: String,
    @ColumnInfo(name = "Energy_in_kJ")
    val energy_in_kJ: Double,
    @ColumnInfo(name = "Energy_in_kcal")
    val energy_in_kcal: Double,
    @ColumnInfo(name = "Water_in_g")
    val water_in_g: Double,
    @ColumnInfo(name = "Protein_in_g")
    val protein_in_g: Double,
    @ColumnInfo(name = "Fat_in_g")
    val fat_in_g: Double,
    @ColumnInfo(name = "Carbohydrate_available_in_g")
    val carbohydrate_available_in_g: Double,
    @ColumnInfo(name = "Fibre_in_g")
    val fibre_in_g: Double,
    @ColumnInfo(name = "Ash_in_g")
    val ash_in_g: Double,
    @ColumnInfo(name = "Ca_in_mg")
    val ca_in_mg: Double,
    @ColumnInfo(name = "Fe_in_mg")
    val fe_in_mg: Double,
    @ColumnInfo(name = "Mg_in_mg")
    val mg_in_mg: Double,
    @ColumnInfo(name = "P_in_mg")
    val p_in_mg: Double,
    @ColumnInfo(name = "K_in_mg")
    val k_in_mg: Double,
    @ColumnInfo("Na_in_mg")
    val na_in_mg: Double,
    @ColumnInfo(name = "Zn_in_mg")
    val zn_in_mg: Double,
    @ColumnInfo(name = "Se_in_mg")
    val se_in_mg: Double,
    @ColumnInfo(name = "Vit_A_RAE_in_mcg")
    val vit_a_rae_in_mcg: Double,
    @ColumnInfo(name = "Vit_A_RE_in_mcg")
    val vit_a_re_in_mcg: Double,
    @ColumnInfo(name = "Retinol_in_mcg")
    val retinol_in_mcg: Double,
    @ColumnInfo(name = "Beta_carotene_equivalent_in_mcg")
    val beta_carotene_equivalent_in_mcg: Double,
    @ColumnInfo(name = "Thiamin_in_mcg")
    val thiamin_in_mcg: Double,
    @ColumnInfo(name = "Riboflavin_in_mcg")
    val riboflavin_in_mcg: Double,
    @ColumnInfo(name = "Niacin_in_mcg")
    val niacin_in_mcg: Double,
    @ColumnInfo(name = "Dietary_folate_eq_in_mcg")
    val dietary_folate_eq_in_mcg: Double,
    @ColumnInfo(name = "Food_folate_in_mcg")
    val food_folate_in_mcg: Double,
    @ColumnInfo(name = "Vit_B12_mcg")
    val vit_b12_mcg: Double,
    @ColumnInfo(name = "Vit_C_in_mcg")
    val vit_c_in_mcg: Double,
    @ColumnInfo(name = "Cholesterol_in_mcg")
    val cholesterol_mg: Double,
    @ColumnInfo(name = "Dish_group_code")
    val dish_group_code: Int?,
    @ColumnInfo(name = "Dish_group_name")
    val dish_group_name: String?,
    @ColumnInfo(name = "Food_description")
    val food_description: String?,
    @ColumnInfo(name = "Food_ingredients")
    val food_ingredients: String?,
    @ColumnInfo(name = "Food_preparation_cooking_serves_makes")
    val food_preparation_cooking_serves_makes: String?,
    @ColumnInfo(name = "Dish_time")
    val dish_time: String?
)
