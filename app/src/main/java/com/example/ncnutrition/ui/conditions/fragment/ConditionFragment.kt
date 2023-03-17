package com.example.ncnutrition.ui.conditions.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentConditionBinding
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModel
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModelFactory


class ConditionFragment : Fragment() {

    private val viewModel: ConditionViewModel by activityViewModels {
        ConditionViewModelFactory(
            (activity?.application as NCNutritionApplication).database.conditionDao()
        )
    }

    private var _binding: FragmentConditionBinding? = null
    private val binding get() = _binding!!

    private lateinit var condition: Condition
    
    private fun bind(condition: Condition) {
        binding.apply {
            conditionName.text = condition.description
        }
    }

    private val navigationArgs: ConditionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveCondition(id).observe(this.viewLifecycleOwner) { selectedCondition ->
            condition = selectedCondition
            bind(condition)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}