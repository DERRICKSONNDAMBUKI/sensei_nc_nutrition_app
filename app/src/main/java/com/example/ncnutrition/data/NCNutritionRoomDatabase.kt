package com.example.ncnutrition.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ncnutrition.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class NCNutritionRoomDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO

    companion object {
        private var INSTANCE: NCNutritionRoomDatabase? = null
        fun getDatabase(context: Context): NCNutritionRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context.applicationContext, NCNutritionRoomDatabase::class.java,
                    "ncnutrition_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance

            }
        }
    }

}