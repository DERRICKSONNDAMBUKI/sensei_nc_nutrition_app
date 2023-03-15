package com.example.ncnutrition.data

import androidx.room.*
import com.example.ncnutrition.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(food: Food)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<Food>)

    //    suspend fun insertFoods(vararg foods:Food)
    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("select * from food ORDER BY Code asc")
    fun getFoods(): Flow<List<Food>>

    @Query("select * from food where id = :id")
    fun getFood(id: Int): Flow<Food>

    @Query("select * from food where Food_Group_Code = :food_group_code")
    fun getFoodsByFoodGroup(food_group_code: Int): Flow<List<Food>>

    @Query("select * from food where Food_name LIKE :food_name LIMIT 5")
    fun findFoodByName(food_name: String): Flow<List<Food>>
}