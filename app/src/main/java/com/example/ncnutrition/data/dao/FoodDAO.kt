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

//    energy_in_kcal
    @Query("select energy_in_kcal from food ORDER BY energy_in_kcal asc")
    fun getEnergyInKcal(): Flow<List<Double>>

    @Query("select * from food where energy_in_kcal > :Q2  ORDER BY energy_in_kcal asc ")
    fun getRichEnergyFoods(Q2: Double): Flow<List<Food>>

    @Query("select * from food where energy_in_kcal < :Q2 ORDER BY energy_in_kcal asc ")
    fun getLowEnergyFoods(Q2: Double): Flow<List<Food>>

    @Query("select * from food where energy_in_kcal between :Q1 and :Q3 ORDER BY energy_in_kcal asc ")
    fun getRegularEnergyFoods(Q1: Double, Q3: Double): Flow<List<Food>>

}