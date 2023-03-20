package com.example.ncnutrition.model

import androidx.room.*

@Fts4
@Entity(tableName = "condition", indices = [Index(value = ["name"], unique = true)])
data class Condition(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("nutrients")
    val nutrients: String
)
