package com.example.ncnutrition

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.ncnutrition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //
//    private val foodsViewModel: FoodViewModel by viewModels {
//        FoodViewModelFactory(
//            (this.application as NCNutritionApplication).database.foodDao()
//        )
//    }


    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView

    private val appBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.navigation_home,
            R.id.navigation_conditions,
            R.id.navigation_deficiencies,
            R.id.navigation_foods,
            R.id.navigation_recipes,
//            R.id.profile,
            R.id.search,
//            R.id.notifications_menu_item,
            R.id.navigation_meals,
            R.id.navigation_progress,
            R.id.navigation_tools
        ), fallbackOnNavigateUpListener = ::onSupportNavigateUp
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment.navController

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
//        search
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView

//         // Inflate the options menu from XML
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {

//            Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

//        initQueryListener()

        return true
    }

}