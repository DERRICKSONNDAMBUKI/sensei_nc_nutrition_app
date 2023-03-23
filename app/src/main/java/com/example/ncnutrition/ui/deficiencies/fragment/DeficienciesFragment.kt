package com.example.ncnutrition.ui.deficiencies.fragment

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
import com.example.ncnutrition.databinding.FragmentDeficienciesListBinding
import com.example.ncnutrition.ui.deficiencies.adapter.DeficienciesAdapter
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModel
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class DeficienciesFragment : Fragment() {

    private val viewModel: DeficiencyViewModel by activityViewModels {
        DeficiencyViewModelFactory(
            (activity?.application as NCNutritionApplication).database.deficiencyDao(),
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private var _binding: FragmentDeficienciesListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeficienciesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DeficienciesAdapter { deficiency ->
//            call update by sending deficiency

                viewModel.updateDeficiency(deficiency)
                Toast.makeText(context,"updating ${deficiency.name}",Toast.LENGTH_SHORT).show()

            val action =
                DeficienciesFragmentDirections.actionNavigationDeficienciesToDeficiencyFragment(
                    deficiency.id
                )
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.allDeficiencies.observe(this.viewLifecycleOwner) { deficiency ->
            deficiency.let {
                adapter.submitList(it)
            }
            if (deficiency.isEmpty()) {
                Toast.makeText(this.context, "no deficiencies", Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}