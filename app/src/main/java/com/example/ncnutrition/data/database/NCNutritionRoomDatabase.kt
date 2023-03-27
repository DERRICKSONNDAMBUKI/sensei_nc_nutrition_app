package com.example.ncnutrition.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.data.dao.MealDAO
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.model.Food
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

@Database(
    entities = [Food::class, Condition::class, Deficiency::class], version = 1, exportSchema = false
)
@TypeConverters(FoodListConverters::class, MealListConverters::class)
abstract class NCNutritionRoomDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    abstract fun conditionDao(): ConditionDAO
    abstract fun deficiencyDao(): DeficiencyDAO
    abstract fun mealDao(): MealDAO


    companion object {
        private var INSTANCE: NCNutritionRoomDatabase? = null
        fun getDatabase(context: Context): NCNutritionRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NCNutritionRoomDatabase::class.java,
                    "ncnutrition_database"
                ).fallbackToDestructiveMigration().build()

                runBlocking {
                    instance.foodDao().insertAll(
                        foods = importFoodsJson(context)
                    )
                    instance.conditionDao().insertAll(
                        conditions = importConditionsJson(context)
                    )
                    importDeficienciesJson(context).forEach {
                        instance.deficiencyDao().insert(it)
                    }

                }

                INSTANCE = instance
                return instance

            }
        }
    }
}

// read json data from file
private fun readJsonFromFile(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

private fun importFoodsJson(context: Context): List<Food> {
    val json =
        readJsonFromFile(context, "sensei_kenya_food_composition_data_with_KRB_2018 - Sheet2.json")
    val foodsList = Gson().fromJson(json, Array<Food>::class.java).toList()
    return foodsList.map {
        Food(
            food_group_code = it.food_group_code,
            food_group = it.food_group,
            code = it.code,
            food_name = it.food_name,
            edible_conversion_factor = it.edible_conversion_factor,
            energy_in_kJ = it.energy_in_kJ,
            energy_in_kcal = it.energy_in_kcal,
            water_in_g = it.water_in_g,
            protein_in_g = it.protein_in_g,
            fat_in_g = it.fat_in_g,
            carbohydrate_available_in_g = it.carbohydrate_available_in_g,
            fibre_in_g = it.fibre_in_g,
            ash_in_g = it.ash_in_g,
            ca_in_mg = it.ca_in_mg,
            fe_in_mg = it.fe_in_mg,
            mg_in_mg = it.mg_in_mg,
            p_in_mg = it.p_in_mg,
            k_in_mg = it.k_in_mg,
            na_in_mg = it.na_in_mg,
            zn_in_mg = it.zn_in_mg,
            se_in_mg = it.se_in_mg,
            vit_a_rae_in_mcg = it.vit_a_rae_in_mcg,
            vit_a_re_in_mcg = it.vit_a_re_in_mcg,
            retinol_in_mcg = it.retinol_in_mcg,
            beta_carotene_equivalent_in_mcg = it.beta_carotene_equivalent_in_mcg,
            thiamin_in_mcg = it.thiamin_in_mcg,
            riboflavin_in_mcg = it.riboflavin_in_mcg,
            niacin_in_mcg = it.niacin_in_mcg,
            dietary_folate_eq_in_mcg = it.dietary_folate_eq_in_mcg,
            food_folate_in_mcg = it.food_folate_in_mcg,
            vit_b12_in_mcg = it.vit_b12_in_mcg,
            vit_c_in_mcg = it.vit_c_in_mcg,
            cholesterol_in_mg = it.cholesterol_in_mg,
            dish_group_code = it.dish_group_code,
            dish_group_name = it.dish_group_name,
            dish_group_description = it.dish_group_description,
            food_description = it.food_description,
            food_ingredients = it.food_ingredients,
            food_preparation_cooking_serves_makes = it.food_preparation_cooking_serves_makes,
            dish_time = it.dish_time
        )
    }
}

private fun importConditionsJson(context: Context): List<Condition> {
    val json = readJsonFromFile(context, "nutritional_conditions.json")
    val conditionsList = Gson().fromJson(json, Array<Condition>::class.java).toList()
    return conditionsList.map {
        Condition(name = it.name, description = it.description, nutrients = it.nutrients)
    }
}

private fun importDeficienciesJson(context: Context): List<Deficiency> {
    val json = readJsonFromFile(context, "nutritional_deficiencies.json")
    val deficienciesList = Gson().fromJson(json, Array<Deficiency>::class.java).toList()
    return deficienciesList.map {
        Deficiency(
            name = it.name,
            sign_and_symptoms = it.sign_and_symptoms,
            nutrients = it.nutrients,
            function = it.function,
        )
    }
}