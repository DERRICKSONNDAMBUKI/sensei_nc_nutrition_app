package com.example.ncnutrition.ui.deficiencies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentDeficiencyBinding
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModel
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModelFactory
import com.example.ncnutrition.ui.foods.adapter.FoodsAdapter


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
    private lateinit var deficiencyFoods: List<Food>

    private fun bind(deficiency: Deficiency) {
        binding.apply {
            deficiencyName.text = deficiency.name
            deficiencySignsAndSymptoms.text = deficiency.sign_and_symptoms
            deficiencyNutrients.text = deficiency.nutrients
            deficiencyFunction.text = deficiency.function
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

        val deficiencyFoodsRecyclerView = binding.deficiencyFoodsRecyclerView

        val adapter = FoodsAdapter { food ->
            val action =
                DeficiencyFragmentDirections.actionDeficiencyFragmentToFoodFragment(food.code)
            this.findNavController().navigate(action)
        }
//        set adapter
        deficiencyFoodsRecyclerView.adapter = adapter

        val id = navigationArgs.id
        viewModel.retrieveDeficiency(id).observe(this.viewLifecycleOwner) { selectedDeficiency ->
            deficiency = selectedDeficiency
            bind(deficiency)
        }
        viewModel.getDeficiencyFoods(id).observe(this.viewLifecycleOwner) { foods ->
            deficiencyFoods = foods
            if (deficiencyFoods.isEmpty()) {
                Toast.makeText(this.context, "no foods", Toast.LENGTH_SHORT).show()
            } else {
                deficiencyFoods.let {
                    adapter.submitList(it)
                }
                Toast.makeText(this.context, "${deficiencyFoods.count()} foods", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        deficiencyFoodsRecyclerView.isNestedScrollingEnabled = false
        deficiencyFoodsRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}