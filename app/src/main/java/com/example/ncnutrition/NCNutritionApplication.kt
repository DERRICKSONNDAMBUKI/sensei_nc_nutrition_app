package com.example.ncnutrition

import android.app.Application
import com.example.ncnutrition.data.database.NCNutritionRoomDatabase

class NCNutritionApplication : Application() {
    val database: NCNutritionRoomDatabase by lazy { NCNutritionRoomDatabase.getDatabase(this) }
}