package com.example.ncnutrition.data.database

import androidx.room.TypeConverter
import com.example.ncnutrition.model.Food
import com.google.gson.Gson
import java.util.*


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
class  MealListConverters{
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}