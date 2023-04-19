package com.example.ncnutrition

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.example.ncnutrition.databinding.ActivityMainBinding
import com.example.ncnutrition.databinding.ActivitySearchableBinding
import com.example.ncnutrition.ui.search.search_view.SearchRecyclerViewAdapter
import com.example.ncnutrition.ui.search.viewModel.SearchViewModel
import com.example.ncnutrition.ui.search.viewModel.SearchViewModelFactory

class SearchableActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            (this.application as NCNutritionApplication).database.foodDao(),
            (this.application as NCNutritionApplication).database.conditionDao(),
            (this.application as NCNutritionApplication).database.deficiencyDao()
        )
    }

    private lateinit var binding: ActivitySearchableBinding

    private lateinit var progressSpinner: ProgressBar
    private lateinit var searchNotFound: TextView
    private lateinit var searchList: RecyclerView


    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressSpinner = binding.progressSpinner
        searchList = binding.recyclerViewSearchList
        searchNotFound = binding.searchNotFound

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                getSearched(query)
                Toast.makeText(this, "query: $query", Toast.LENGTH_SHORT).show()
            }
        }

        initSearchListView()
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

//    private fun initQueryListener() {
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView.apply {
//            queryHint = getString(R.string.search_hint)
//
//
//            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    getSearched(query)
//                    searchViewModel.searchQuery(query)
//                    return true
//                }
//
//                override fun onQueryTextChange(query: String): Boolean {
//                    // This resets the notes list to display all notes if the query is
//                    // cleared.
//                    progressSpinner.visibility = View.VISIBLE
//                    if (query.isEmpty()) searchViewModel.searchQuery()
//                    return true
//                }
//            })
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
//    }


    private fun getSearched(query: String) {
        searchViewModel.searchQuery(query).observe(this) { foods ->
            foods.let {
                searchRecyclerViewAdapter.submitList(it)
            }
            if (foods.isNullOrEmpty()) {
                searchList.visibility = View.GONE
                searchNotFound.visibility = View.VISIBLE
            } else {


                searchList.visibility = View.VISIBLE
                searchNotFound.visibility = View.GONE
            }
        }
    }
}