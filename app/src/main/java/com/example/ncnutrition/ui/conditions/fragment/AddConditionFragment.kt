package com.example.ncnutrition.ui.conditions.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentAddConditionBinding
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModel
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModelFactory

class AddConditionFragment : Fragment() {
    private val viewModel: ConditionViewModel by activityViewModels {
        ConditionViewModelFactory(
            (activity?.application as NCNutritionApplication).database.conditionDao(),
            (activity?.application as NCNutritionApplication).database.foodDao(),
        )
    }
    lateinit var condition: Condition

    private var _binding: FragmentAddConditionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
//        return viewModel.isEntryValid(
//            get entries from fragment
//        binding.name.text.toString(),
//       binding.description.text.toString()
//        binding.itemPrice.text.toString(),
//        )
        return false // comment
    }

    private fun addNewCondition() {
        if (isEntryValid()) {
//            viewModel.addNewCondition(
//                //      get entries from fragment
////        binding.name.text.toString(),
////       binding.description.text.toString()
////        binding.itemPrice.text.toString(),
//            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}