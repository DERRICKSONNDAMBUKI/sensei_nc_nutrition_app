package com.example.ncnutrition.ui.deficiencies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentAddDeficiencyBinding
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModel
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModelFactory

class AddDeficiencyFragment : Fragment() {

    private val viewModel: DeficiencyViewModel by activityViewModels {
        DeficiencyViewModelFactory(
            (activity?.application as NCNutritionApplication).database.deficiencyDao(),
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }
    lateinit var deficiency: Deficiency

    private var _binding: FragmentAddDeficiencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDeficiencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
//        return viewModel.isEntryValid(
//            binding.apply {
        //        return viewModel.isEntryValid(
//            get entries from fragment
//        name.text.toString(),
        //        signs_and_symptoms.text.toString(),
//       nutrients.text.toString()
//      function.text.toString(),
//        )
//            }
//        )
        return false
    }

    private fun addNewDeficiency() {
        if (isEntryValid()) {
//            viewModel.addNewDeficiency(
            //            binding.apply {
//        name.text.toString(),
            //        signs_and_symptoms.text.toString(),
//       nutrients.text.toString()
//      function.text.toString(),
//        )
//            }
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