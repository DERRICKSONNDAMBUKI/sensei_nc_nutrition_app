package com.example.ncnutrition.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader

@Database(entities = [Food::class,Condition::class], version = 1, exportSchema = false)
abstract class NCNutritionRoomDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    abstract fun conditionDao():ConditionDAO

    companion object {
        private var INSTANCE: NCNutritionRoomDatabase? = null
        fun getDatabase(context: Context): NCNutritionRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, NCNutritionRoomDatabase::class.java,
                    "ncnutrition_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                runBlocking {
                    instance.foodDao().insertAll(importFoodsFromCsv(context,"sensei_kenya_food_composition_data_with_KRB_2018 - Sheet2.csv"))
                }
                INSTANCE = instance
                return instance

            }
        }
    }

}

private fun importFoodsFromCsv(context: Context, foodsFileName:String): MutableList<Food> {
    val foodsInputStream  = context.assets.open(foodsFileName)
    val foodsReader = BufferedReader(InputStreamReader(foodsInputStream))
    val foodList = mutableListOf<Food>()
    foodsReader.useLines { lines->
        lines.forEach {
            val values = it.split(",")
            val food = Food(
                food_group_code = values[0].toInt(),
                food_group = values[1],
                code = values[2],
                food_name = values[3],
                edible_conversion_factor = values[4].toDouble(),
                energy_in_kJ = values[5].toDouble(),
                energy_in_kcal = values[6].toDouble(),
                water_in_g = values[7].toDouble(),
                protein_in_g = values[8].toDouble(),
                fat_in_g = values[9].toDouble(),
                carbohydrate_available_in_g = values[10].toDouble(),
                fibre_in_g = values[11].toDouble(),
                ash_in_g = values[12].toDouble(),
                ca_in_mg = values[13].toDouble(),
                fe_in_mg = values[14].toDouble(),
                mg_in_mg = values[15].toDouble(),
                p_in_mg = values[16].toDouble(),
                k_in_mg = values[17].toDouble(),
                na_in_mg = values[18].toDouble(),
                zn_in_mg = values[19].toDouble(),
                se_in_mg = values[20].toDouble(),
                vit_a_rae_in_mcg = values[21].toDouble(),
                vit_a_re_in_mcg = values[22].toDouble(),
                retinol_in_mcg = values[23].toDouble(),
                beta_carotene_equivalent_in_mcg = values[24].toDouble(),
                thiamin_in_mcg = values[25].toDouble(),
                riboflavin_in_mcg = values[26].toDouble(),
                niacin_in_mcg = values[27].toDouble(),
                dietary_folate_eq_in_mcg = values[28].toDouble(),
                food_folate_in_mcg = values[29].toDouble(),
                vit_b12_mcg = values[30].toDouble(),
                vit_c_in_mcg = values[31].toDouble(),
                cholesterol_mg = values[32].toDouble(),
                dish_group_code = values[33].toInt(),
                dish_group_name = values[34],
                dish_group_description=values[35],
                food_description = values[36],
                food_ingredients = values[37],
                food_preparation_cooking_serves_makes =values[38],
                dish_time = values[39]
            )
            foodList.add(food)
        }
    }
    return foodList
}