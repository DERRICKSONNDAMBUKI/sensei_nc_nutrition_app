package com.example.ncnutrition.data.dao

import androidx.room.*
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Food)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<Food>)

    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("select * from food ORDER BY Code asc")
    fun getFoods(): Flow<List<Food>>

    @Query("select * from food where code = :code")
    fun getFood(code: String): Flow<Food>

    @Query("select * from food where Food_Group_Code = :food_group_code")
    fun getFoodsByFoodGroup(food_group_code: Int): Flow<List<Food>>

    @Query("select * from food where Food_name LIKE :food_name LIMIT 5")
    fun findFoodByName(food_name: String): Flow<List<Food>>

    @Query("select :nutrient from food order by :nutrient asc")
    fun getNutrient(nutrient: String): Flow<List<Double>>

    @Query("select * from food where :nutrient > :Q2 ORDER BY :nutrient asc ")
    fun getHighNutrientFoods(nutrient: String, Q2: Double): Flow<List<Food>?>

    @Query("select * from food where :nutrient < :Q2 ORDER BY :nutrient asc ")
    fun getLowNutrientFoods(nutrient: String, Q2: Double): Flow<List<Food>?>

//    @Query("select * from food where :nutrient > :Q1 AND < :Q3 ORDER BY :nutrient asc ")
//    fun getRegularNutrientFoods(nutrient: String, Q1: Double, Q3: Double): Flow<List<Food>>

}