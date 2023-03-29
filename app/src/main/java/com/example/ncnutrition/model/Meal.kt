package com.example.ncnutrition.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity("meal")
data class Meal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("rowid") val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("date") val date: Date,
//    @ColumnInfo("foods")
//    val foods:List<Food>
    @ColumnInfo("food") val food: Food
)
