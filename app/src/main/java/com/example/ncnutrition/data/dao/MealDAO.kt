package com.example.ncnutrition.data.dao

import androidx.room.*
import com.example.ncnutrition.model.Meal
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meal: List<Meal>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("select * from meal order by date desc")
    fun getMeals():Flow<List<Meal>>

    @Query("select * from meal where date = :date")
    fun getMeal(date:Date):Flow<List<Meal>>

    @Query("select * from meal where date like :date limit 10")
    fun findMealByDate(date: Date):Flow<List<Meal>>

}