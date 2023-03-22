package com.example.ncnutrition.data.database

import androidx.room.TypeConverter
import com.example.ncnutrition.model.Food
import com.google.gson.Gson

class FoodListConverters {
    @TypeConverter
    fun listToJson(value: List<Food>?):String{
        return Gson().toJson(value)
    }
    @TypeConverter
    fun jsonToList(value: String):List<Food>?{
        return Gson().fromJson(value,Array<Food>::class.java)?.toList()
    }
}