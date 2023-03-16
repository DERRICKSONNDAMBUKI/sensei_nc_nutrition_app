package com.example.ncnutrition.data.database

import android.app.Application

class NCNutritionApplication:Application() {
    val database: NCNutritionRoomDatabase by lazy { NCNutritionRoomDatabase.getDatabase(this) }
}