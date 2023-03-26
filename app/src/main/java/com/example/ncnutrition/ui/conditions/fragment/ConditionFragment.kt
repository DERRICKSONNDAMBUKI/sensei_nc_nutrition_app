package com.example.ncnutrition.ui.conditions.fragment

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
import com.example.ncnutrition.databinding.FragmentConditionBinding
import com.example.ncnutrition.model.Condition
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModel
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModelFactory
import com.example.ncnutrition.ui.foods.adapter.FoodsAdapter


class ConditionFragment : Fragment() {

    private val viewModel: ConditionViewModel by activityViewModels {
        ConditionViewModelFactory(
            (activity?.application as NCNutritionApplication).database.conditionDao(),
            (activity?.application as NCNutritionApplication).database.foodDao(),
        )
    }

    private var _binding: FragmentConditionBinding? = null
    private val binding get() = _binding!!

    private lateinit var condition: Condition
    private lateinit var conditionFoods: List<Food>

    private fun bind(condition: Condition) {
        binding.apply {
            conditionName.text = condition.name
            conditionDescription.text = condition.description
            conditionNutrients.text = condition.nutrients
        }
    }


    private val navigationArgs: ConditionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodsAdapter { food ->
            val action =
                ConditionFragmentDirections.actionConditionFragmentToFoodFragment(food.code)
            this.findNavController().navigate(action)
        }
//        set adapter

        binding.apply {
           conditionFoodsRecyclerView.adapter = adapter
        }


        val id = navigationArgs.id

        viewModel.getConditionFoods(id).observe(this.viewLifecycleOwner) { foods ->
            conditionFoods = foods
            if (conditionFoods.isEmpty()) {
                Toast.makeText(this.context, "no foods", Toast.LENGTH_SHORT).show()
            } else {
                conditionFoods.let {
                    adapter.submitList(it)
                }
                Toast.makeText(this.context, "${conditionFoods.count()} foods", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.conditionFoodsRecyclerView.layoutManager = LinearLayoutManager(this.context)

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