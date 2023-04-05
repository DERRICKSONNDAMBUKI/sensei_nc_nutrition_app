package com.example.ncnutrition.ui.tools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentToolsBinding

class ToolsFragment : Fragment() {

    private val viewModel: ToolsViewModel by activityViewModels {
        ToolsViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private var _binding: FragmentToolsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.measureBMI.setOnClickListener {
            measureBMI()
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            weight = binding.editTextNumberDecimalWeight.text.toString().toDoubleOrNull(),
            height = binding.editTextNumberDecimalHeight.text.toString().toDoubleOrNull()
        )
    }

    private fun measureBMI() {
        if (isEntryValid()) {
            val weight = binding.editTextNumberDecimalWeight.text.toString().toDouble()
            val height = binding.editTextNumberDecimalHeight.text.toString().toDouble()
            binding.apply {
                textViewBMIResult.visibility = View.VISIBLE
                textViewBMIResult.text = viewModel.getBMI(weight, height)
                editTextNumberDecimalHeight.text.clear()
                editTextNumberDecimalWeight.text.clear()
            }

        } else {
            binding.textViewBMIResult.visibility = View.INVISIBLE
            Toast.makeText(context, "Invalid weight/height", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}