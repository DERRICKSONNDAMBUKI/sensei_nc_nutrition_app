package com.example.ncnutrition.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ncnutrition.data.dao.ConditionDAO
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.data.dao.FoodDAO
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.model.Food
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

@Database(
    entities = [Food::class, Condition::class, Deficiency::class],
    version = 1,
    exportSchema = false
)
abstract class NCNutritionRoomDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    abstract fun conditionDao(): ConditionDAO
    abstract fun deficiencyDao(): DeficiencyDAO

    companion object {
        private var INSTANCE: NCNutritionRoomDatabase? = null
        fun getDatabase(context: Context): NCNutritionRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, NCNutritionRoomDatabase::class.java,
                    "ncnutrition_database"
                )
//                    .addCallback(StartingData(context))
                    .fallbackToDestructiveMigration()
                    .build()

                runBlocking {
//                    instance.foodDao().insertAll(importFoodsFromCsv(context,"database/sensei_kenya_food_composition_data_with_KRB_2018 - Sheet2.csv"))
////                    instance.conditionDao().insertAll(importData(context))
                    instance.conditionDao().insertAll(
                        conditions = importData(context)
                    )
                    importDeficienciesJson(context).forEach {
                        instance.deficiencyDao().insert(it)
                    }

                    instance.foodDao().insert(
//                        mutableListOf(
                        Food(
//                            1,
                            food_group_code = 15,
                            food_group = "Mixed dishes",
                            code = "15066s",
                            food_name = "Biryani stew",
                            edible_conversion_factor = 0.0,
                            energy_in_kJ = 822.0,
                            energy_in_kcal = 198.0,
                            water_in_g = 65.3,
                            protein_in_g = 8.1,
                            fat_in_g = 14.3,
                            carbohydrate_available_in_g = 8.3,
                            fibre_in_g = 1.9,
                            ash_in_g = 2.1,
                            ca_in_mg = 28.0,
                            fe_in_mg = 5.2,
                            mg_in_mg = 24.0,
                            p_in_mg = 139.0,
                            k_in_mg = 332.0,
                            na_in_mg = 480.0,
                            zn_in_mg = 1.58,
                            se_in_mg = 0.008,
                            vit_a_rae_in_mcg = 24.0,
                            vit_a_re_in_mcg = 43.0,
                            retinol_in_mcg = 6.0,
                            beta_carotene_equivalent_in_mcg = 225.0,
                            thiamin_in_mcg = 40.0,
                            riboflavin_in_mcg = 130.0,
                            niacin_in_mcg = 1800.0,
                            dietary_folate_eq_in_mcg = 14.0,
                            food_folate_in_mcg = 14.0,
                            vit_b12_mcg = 0.47,
                            vit_c_in_mcg = 14500.0,
                            cholesterol_mg = 21.0,
                            dish_group_code = 7,
                            dish_group_name = "Meats, Fish & Eggs",
                            dish_group_description = "This section features different kinds of meats, fish and egg recipes that are common in Kenya. The various methods of cooking these proteins are explained.",
                            food_description = "Biryani stew is a traditional delicacy of the Swahili community. It is made with meat and heavily spiced to produce a rich tasty meal. This recipe is sometimes served during special occasions such as weddings or other social ceremonies. It is eaten by the whole family and can be served for lunch or dinner usually accompanied by a colourful biryani rice.",
                            food_ingredients = "\n1 kg beef, raw, medium fat \n8 onions, red skinned, raw, unpeeled (806 g) \n2 potatoes, unpeeled Irish, white, raw (454 g) \n9 tomatoes, red, ripe (1.1 kg) 1 cup (163 g) tomato paste, salted \n1 bunch (60 g) fresh coriander \n1 capsicum, green (242 g) \n2 1/3 cups (468 g) cooking oil \n1 piece (20 g) ginger \n1 garlic, whole (46 g) \n2 1⁄2 tbsp. (37 g) salt, iodized \n4 tbsp. (18 g) pilau masala \n1 cup (242 g) maziwa mala (fermented milk) \n1 1⁄4 tsp. (2 g) turmeric powder \n2 1⁄4 cups (511 g) water",
                            food_preparation_cooking_serves_makes = "\nWash and cut the meat. \nBoil the meat in 1 cup of water for 20 minutes until meat is tender. \nPrepare and put coriander, capsicum, garlic, ginger and tomatoes into a blender and blend to a medium thick juice. Add 1 1⁄4 cups of water. \nPeel, wash and chop the onions into a separate bowl. \nPut oil into a cooking pot and heat for 1 minute. \nAdd the chopped onions. \nCook onions until golden brown. \nAdd the blended paste and stir. \nAdd meat, turmeric, tomato paste, potatoes, pilau masala and salt. \nCover and simmer until the potatoes are cooked or soft. \nAdd the fermented milk and continue to simmer for 5 minutes. \nStir and remove from fire. \nServe with the biryani rice.",
                            dish_time = "Preparation 20 minutes | Cooking 1 hour 10 minutes | Serves 6"
                        ),
                    )

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

private fun importData(context: Context): List<Condition> {
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
            function = it.function
        )
    }
}
//private fun importFoodsFromCsv(context: Context, foodsFileName: String): MutableList<Food> {
//    val foodsInputStream = context.assets.open(foodsFileName)
//    val foodsReader = BufferedReader(InputStreamReader(foodsInputStream))
//    val foodList = mutableListOf<Food>()
//    foodsReader.useLines { lines ->
//        lines.forEach {
//            val values = it.split(",")
//            val food = Food(
//                food_group_code = values[0].toIntOrNull(),
//                food_group = values[1],
//                code = values[2],
//                food_name = values[3],
//                edible_conversion_factor = values[4].toDoubleOrNull(),
//                energy_in_kJ = values[5].toDoubleOrNull(),
//                energy_in_kcal = values[6].toDoubleOrNull(),
//                water_in_g = values[7].toDoubleOrNull(),
//                protein_in_g = values[8].toDoubleOrNull(),
//                fat_in_g = values[9].toDoubleOrNull(),
//                carbohydrate_available_in_g = values[10].toDoubleOrNull(),
//                fibre_in_g = values[11].toDoubleOrNull(),
//                ash_in_g = values[12].toDoubleOrNull(),
//                ca_in_mg = values[13].toDoubleOrNull(),
//                fe_in_mg = values[14].toDoubleOrNull(),
//                mg_in_mg = values[15].toDoubleOrNull(),
//                p_in_mg = values[16].toDoubleOrNull(),
//                k_in_mg = values[17].toDoubleOrNull(),
//                na_in_mg = values[18].toDoubleOrNull(),
//                zn_in_mg = values[19].toDoubleOrNull(),
//                se_in_mg = values[20].toDoubleOrNull(),
//                vit_a_rae_in_mcg = values[21].toDoubleOrNull(),
//                vit_a_re_in_mcg = values[22].toDoubleOrNull(),
//                retinol_in_mcg = values[23].toDoubleOrNull(),
//                beta_carotene_equivalent_in_mcg = values[24].toDoubleOrNull(),
//                thiamin_in_mcg = values[25].toDoubleOrNull(),
//                riboflavin_in_mcg = values[26].toDoubleOrNull(),
//                niacin_in_mcg = values[27].toDoubleOrNull(),
//                dietary_folate_eq_in_mcg = values[28].toDoubleOrNull(),
//                food_folate_in_mcg = values[29].toDoubleOrNull(),
//                vit_b12_mcg = values[30].toDoubleOrNull(),
//                vit_c_in_mcg = values[31].toDoubleOrNull(),
//                cholesterol_mg = values[32].toDoubleOrNull(),
//                dish_group_code = values[33].toIntOrNull(),
//                dish_group_name = values[34],
//                dish_group_description = values[35],
//                food_description = values[36],
//                food_ingredients = values[37],
//                food_preparation_cooking_serves_makes = values[38],
//                dish_time = values[39]
//            )
//            foodList.add(food)
//        }
//    }
//    println(foodList)
//    return foodList
//}