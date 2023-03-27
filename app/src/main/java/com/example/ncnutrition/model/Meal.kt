package com.example.ncnutrition.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity("meal",indices = [androidx.room.Index(value = ["name"], unique = true)])
data class Meal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("rowid")
    val id:Int,
    @ColumnInfo("name")
    val name:String,
    @ColumnInfo("date")
    val date: Date,
    @ColumnInfo("foods")
    val foods:List<Food>
)
