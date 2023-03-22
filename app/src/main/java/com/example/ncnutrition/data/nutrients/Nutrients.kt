package com.example.ncnutrition.data.nutrients

//import com.example.ncnutrition.data.dao.FoodDAO
//import com.example.ncnutrition.model.Food
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.toList
//import kotlinx.coroutines.runBlocking
//
//@Suppress("UNCHECKED_CAST")
//interface Nutrients {
//
//    val foodDAO: FoodDAO
//
//    private suspend fun getPercentile(data: Flow<List<Double>>, percentile: Double): Double {
//
//        val maxValue = data.toList().maxOf {
//            it.max()
//        }
//        return (percentile / 100).times(maxValue)
//    }
//
//     fun getFoodsByNutrients(nutrient: String, level: String): List<Food> {
//         val nutrientValues = foodDAO.getNutrient(nutrient)
//
////        percentiles
//         return runBlocking {
//             val q1 = getPercentile(nutrientValues, 25.0)
//             val q2 = getPercentile(nutrientValues, 50.0)
//             val q3 = getPercentile(nutrientValues, 75.0)
//             return@runBlocking (when (level) {
//                 "high" -> foodDAO.getHighNutrientFoods(nutrient, q2) as List<Food>
//                 "low" -> foodDAO.getLowNutrientFoods(nutrient, q2) as List<Food>
////                 "regular" -> foodDAO.getRegularNutrientFoods(nutrient, q1, q3) as List<Food>
//                 else -> foodDAO.getFoods() as List<Food>
//             })
//         }
//    }
//}

