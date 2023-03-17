package com.example.ncnutrition.ui.deficiencies.viewModel

import androidx.lifecycle.*
import com.example.ncnutrition.data.dao.DeficiencyDAO
import com.example.ncnutrition.model.Deficiency
import kotlinx.coroutines.launch

class DeficiencyViewModel(private val deficiencyDAO: DeficiencyDAO) : ViewModel() {

    val allDeficiencies: LiveData<List<Deficiency>> = deficiencyDAO.getDeficiencies().asLiveData()

    private fun insertDeficiency(deficiency: Deficiency) {
        viewModelScope.launch {
            deficiencyDAO.insert(deficiency)
        }
    }

    private fun getNewDeficiencyEntry(
        name: String,
        sign_and_symptoms: String,
        nutrients: String,
        function: String
    ): Deficiency {
        return Deficiency(
            name = name,
            sign_and_symptoms = sign_and_symptoms,
            nutrients = nutrients,
            function = function
        )
    }

    fun addNewDeficiency(
        name: String,
        sign_and_symptoms: String,
        nutrients: String,
        function: String
    ) {
        val newDeficiency = getNewDeficiencyEntry(
            name, sign_and_symptoms, nutrients, function
        )
        insertDeficiency(newDeficiency)
    }

    fun isEntryValid(
        name: String,
        sign_and_symptoms: String,
        nutrients: String,
        function: String
    ): Boolean {
        if (name.isBlank() || sign_and_symptoms.isBlank() || nutrients.isBlank() || function.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveDeficiency(id: Int): LiveData<Deficiency> {
        return deficiencyDAO.getDeficiency(id).asLiveData()
    }
}

class DeficiencyViewModelFactory(private val deficiencyDAO: DeficiencyDAO) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeficiencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeficiencyViewModel(deficiencyDAO) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}