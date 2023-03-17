package com.example.ncnutrition.ui.conditions.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentConditionsListBinding
import com.example.ncnutrition.ui.conditions.adapter.ConditionsAdapter
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModel
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class ConditionsFragment : Fragment() {
    private val viewModel: ConditionViewModel by activityViewModels {
        ConditionViewModelFactory(
            (activity?.application as NCNutritionApplication).database.conditionDao()
        )
    }
    private var _binding: FragmentConditionsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConditionsListBinding.inflate(inflater, container, false)
        return binding.root
        // Set the adapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ConditionsAdapter { condition ->
            val action =
                ConditionsFragmentDirections.actionConditionsFragmentToConditionFragment(condition.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.allConditions.observe(this.viewLifecycleOwner) { condition ->
            condition.let {
                adapter.submitList(it)
            }
            if (condition.isEmpty()){
                Toast.makeText(this.context, "no conditions", Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
