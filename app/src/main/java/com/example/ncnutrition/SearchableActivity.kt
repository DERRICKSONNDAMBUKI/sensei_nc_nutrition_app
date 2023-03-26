package com.example.ncnutrition

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory

class SearchableActivity : AppCompatActivity() {
    private val foodsViewModel: FoodViewModel by viewModels {
        FoodViewModelFactory(
            (this.application as NCNutritionApplication).database.foodDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                doMySearch(query)
                getSearched(query)
            }
        }
    }



    private fun getSearched(query: String) {
        foodsViewModel.searchFood(query).observe(this@SearchableActivity) { foods ->
            foods.let {
                Log.e("Foods", it.toString())
                Toast.makeText(this@SearchableActivity, "query $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}