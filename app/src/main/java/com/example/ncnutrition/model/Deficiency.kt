package com.example.ncnutrition.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Fts4
@Entity(tableName = "deficiency",indices = [androidx.room.Index(value = ["name"], unique = true)])
data class Deficiency(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("rowid")
    val id: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("sign_and_symptoms")
    val sign_and_symptoms: String,
    @ColumnInfo("nutrients")
    val nutrients: String,
    @ColumnInfo("function")
    val function: String,
    @ColumnInfo("foods")
    val foods: List<Food>? = null,
)
