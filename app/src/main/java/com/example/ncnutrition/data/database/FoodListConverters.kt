package com.example.ncnutrition.data.database

import androidx.room.TypeConverter
import com.example.ncnutrition.model.Food
import com.google.gson.Gson
import java.util.*


class FoodListConverters {
    @TypeConverter
    fun listToJson(value: Food?):String{
        return Gson().toJson(value)
    }
    @TypeConverter
    fun jsonToList(value: String):Food{
        return Gson().fromJson(value,Food::class.java)
    }
}
class  MealListConverters{
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

}