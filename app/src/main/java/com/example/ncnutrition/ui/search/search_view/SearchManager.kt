package com.example.ncnutrition.ui.search.search_view

import android.app.appsearch.AppSearchSession
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchManager(context: Context,coroutineScope: CoroutineScope) {
    private val isInitialized:MutableStateFlow<Boolean> = MutableStateFlow(false)
    private lateinit var appSearchSession: AppSearchSession

    init {
        coroutineScope.launch {

        }
    }
}