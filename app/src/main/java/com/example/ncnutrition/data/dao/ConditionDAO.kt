package com.example.ncnutrition.data.dao

import androidx.room.*
import com.example.ncnutrition.model.Condition
import kotlinx.coroutines.flow.Flow

@Dao
interface ConditionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(condition: Condition)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(conditions: List<Condition>)

    @Update
    suspend fun update(condition: Condition)

    @Delete
    suspend fun delete(condition: Condition)

    @Query("SELECT * FROM condition WHERE rowid=:id")
    fun getCondition(id: Int): Flow<Condition>

    @Query("select * from condition order by name asc")
    fun getConditions(): Flow<List<Condition>>

    @Query("select * from condition where name LIKE :query or description LIKE :query LIMIT 5")
    fun findConditionByName(query: String): Flow<List<Condition>>
}