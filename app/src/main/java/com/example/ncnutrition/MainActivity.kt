package com.example.ncnutrition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.ncnutrition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val appBarConfiguration = AppBarConfiguration(

        setOf(
            R.id.navigation_home,
//                R.id.navigation_notifications,
            R.id.navigation_conditions,
            R.id.navigation_deficiencies,
            R.id.navigation_foods,
            R.id.navigation_dashboard,
            R.id.action_settings
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
//        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}