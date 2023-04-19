package com.example.ncnutrition

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ncnutrition.databinding.ActivityMainBinding
import com.example.ncnutrition.ui.search.search_view.SearchRecyclerViewAdapter
import com.example.ncnutrition.ui.search.viewModel.SearchViewModel
import com.example.ncnutrition.ui.search.viewModel.SearchViewModelFactory

class MainActivity : AppCompatActivity() {
    //
//    private val foodsViewModel: FoodViewModel by viewModels {
//        FoodViewModelFactory(
//            (this.application as NCNutritionApplication).database.foodDao()
//        )
//    }
    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            (this.application as NCNutritionApplication).database.foodDao(),
            (this.application as NCNutritionApplication).database.conditionDao(),
            (this.application as NCNutritionApplication).database.deficiencyDao()
        )
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressSpinner: ProgressBar
    private lateinit var searchNotFound: TextView
    private lateinit var searchList: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter

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

        progressSpinner = binding.progressSpinner
        searchList = binding.recyclerViewSearchList
        searchNotFound = binding.searchNotFound

        initSearchListView()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

    }

    private fun initSearchListView() {
        searchRecyclerViewAdapter = SearchRecyclerViewAdapter {
//           TODO navigation action

        }

        searchList.adapter = searchRecyclerViewAdapter
        searchList.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        searchList.layoutManager = LinearLayoutManager(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
//        search
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        initQueryListener()

        return true
    }

    private fun initQueryListener() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            queryHint = getString(R.string.search_hint)


            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    getSearched(query)
                    searchViewModel.searchQuery(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    // This resets the notes list to display all notes if the query is
                    // cleared.
                    progressSpinner.visibility = View.VISIBLE
                    if (query.isEmpty()) searchViewModel.searchQuery()
                    return true
                }
            })

            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment.navController

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun getSearched(query: String) {
        searchViewModel.searchQuery(query).observe(
            /* owner = */ this
        ) {
            if (it.isNullOrEmpty()) {
                searchList.visibility = View.GONE
                searchNotFound.visibility = View.VISIBLE
            } else {
                searchRecyclerViewAdapter.submitList(it)
                searchList.visibility = View.VISIBLE
                searchNotFound.visibility = View.GONE
            }
        }
    }
}