package com.example.ncnutrition.data.dao

import androidx.room.*
import com.example.ncnutrition.model.Deficiency
import kotlinx.coroutines.flow.Flow

@Dao
interface DeficiencyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deficiency: Deficiency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deficiencies: List<Deficiency>)

    @Update
    suspend fun update(deficiency: Deficiency)

    @Delete
    suspend fun delete(deficiency: Deficiency)

    @Query("SELECT * FROM deficiency WHERE rowid=:id")
    fun getDeficiency(id: Int): Flow<Deficiency>

    @Query("select * from deficiency ORDER BY name")
    fun getDeficiencies(): Flow<List<Deficiency>>

    @Query("select * from deficiency where name like :name limit 5")
    fun findDeficiencyByName(name: String): Flow<List<Deficiency>>
}