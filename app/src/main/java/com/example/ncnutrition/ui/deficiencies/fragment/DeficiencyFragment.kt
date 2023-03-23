package com.example.ncnutrition.ui.deficiencies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentDeficiencyBinding
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModel
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModelFactory


class DeficiencyFragment : Fragment() {

    private val viewModel: DeficiencyViewModel by activityViewModels {
        DeficiencyViewModelFactory(
            (activity?.application as NCNutritionApplication).database.deficiencyDao(),
            (activity?.application as NCNutritionApplication).database.foodDao(),

            )
    }

    private var _binding: FragmentDeficiencyBinding? = null
    private val binding get() = _binding!!

    private lateinit var deficiency: Deficiency

    private fun bind(deficiency: Deficiency) {
        binding.apply {
            deficiencyName.text = deficiency.name
            deficiencySignsAndSymptoms.text = deficiency.sign_and_symptoms
            deficiencyNutrients.text = deficiency.nutrients
            deficiencyFunction.text = deficiency.function
            deficiencyFoods.text = deficiency.foods?.count().toString()
        }
    }

    private val navigationArgs: DeficiencyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeficiencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveDeficiency(id).observe(this.viewLifecycleOwner) { selectedDeficiency ->
            viewModel.updateDeficiency(selectedDeficiency)
            deficiency = selectedDeficiency
            bind(deficiency)
            if (deficiency.foods.isNullOrEmpty()) {
                Toast.makeText(context, "No foods on '${deficiency.name}' yet", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}