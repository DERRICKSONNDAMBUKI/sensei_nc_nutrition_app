package com.example.ncnutrition

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ncnutrition.databinding.ActivityMainBinding
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory

class MainActivity : AppCompatActivity() {
    //    private val viewModel: DeficiencyViewModel by activityViewModels {
//        DeficiencyViewModelFactory(
//            (activity?.application as NCNutritionApplication).database.deficiencyDao(),
//            (activity?.application as NCNutritionApplication).database.foodDao()
//        )
//    }
    private val foodsViewModel: FoodViewModel by viewModels {
        FoodViewModelFactory(
            (this.application as NCNutritionApplication).database.foodDao()
        )
    }

    private lateinit var binding: ActivityMainBinding
    private val appBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.navigation_home,
//                R.id.navigation_notifications,
            R.id.navigation_conditions,
            R.id.navigation_deficiencies,
            R.id.navigation_foods,
            R.id.navigation_dashboard,
//            R.id.action_settings,
            R.id.search
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        val search = menu?.findItem(R.id.search)?.actionView as SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search.apply {
            search.queryHint = "Search..."

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Toast.makeText(this@MainActivity,"query $query",Toast.LENGTH_SHORT).show()
                    getSearched(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
//                    adapter.filter.filter(query)

                    return false
                }
            })


//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

//        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
        return true
    }

    private fun getSearched(query: String) {
        foodsViewModel.searchFood(query).observe(this@MainActivity) { foods ->
            foods.let {
                Log.e("Foods", it.toString())
                Toast.makeText(this@MainActivity,"query $it",Toast.LENGTH_SHORT).show()
            }
        }
    }
}