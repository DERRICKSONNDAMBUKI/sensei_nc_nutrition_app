package com.example.ncnutrition.ui.tools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.R
import com.example.ncnutrition.databinding.FragmentToolsBinding
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModel
import com.example.ncnutrition.ui.conditions.viewModel.ConditionViewModelFactory
import com.example.ncnutrition.ui.tools.viewModel.ToolsViewModel
import com.example.ncnutrition.ui.tools.viewModel.ToolsViewModelFactory
import kotlin.properties.Delegates

class ToolsFragment : Fragment() {

    private val viewModel: ToolsViewModel by activityViewModels {
        ToolsViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private val viewModelCondition: ConditionViewModel by activityViewModels {
        ConditionViewModelFactory(
            (activity?.application as NCNutritionApplication).database.conditionDao(),
            (activity?.application as NCNutritionApplication).database.foodDao(),
        )
    }

    private var _binding: FragmentToolsBinding? = null
    private val binding get() = _binding!!

    private var underWeightId by Delegates.notNull<Int>()
    private var overweightId by Delegates.notNull<Int>()


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

                when (viewModel.getBMI(weight, height)) {
                    "Underweight severe thinness 😒" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Underweight or eating disorders such as Anorexia nervosa and bulimia nervosa"
                        }
                        underWeightId = condition!!.id
                        Toast.makeText(context, "id:$underWeightId", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.underweight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        underWeightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                    "Underweight moderate thinness 😒" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Underweight or eating disorders such as Anorexia nervosa and bulimia nervosa"
                        }
                        underWeightId = condition!!.id
                        Toast.makeText(context, "id:$underWeightId", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.underweight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        underWeightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                    "Underweight mild thinness 😒" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Underweight or eating disorders such as Anorexia nervosa and bulimia nervosa"
                        }
                        underWeightId = condition!!.id
                        Toast.makeText(context, "id:$underWeightId", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.underweight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        underWeightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
//                    normal ->

//                    Underweight or eating disorders such as Anorexia nervosa and bulimia nervosa
//                    Obesity and overweight weight
                    "Overweight or pre-obese 😮" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Obesity and overweight weight"
                        }
                        overweightId = condition!!.id
                        Toast.makeText(context, "id:$overweightId", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.obesity_and_overweight_weight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        overweightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                    "Obese class I 😱" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Obesity and overweight weight"
                        }
                        overweightId = condition!!.id
                        Toast.makeText(context, "id:$overweightId", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.obesity_and_overweight_weight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        overweightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                    "Obese class II 😱" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Obesity and overweight weight"
                        }
                        overweightId = condition!!.id
                        Toast.makeText(context, "id:$overweightId", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.obesity_and_overweight_weight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        overweightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                    "Obese class III 😱" -> viewModelCondition.allConditions.observe(
                        viewLifecycleOwner
                    ) { conditions ->
                        val condition = conditions.find {
                            it.name == "Obesity and overweight weight"
                        }
                        overweightId = condition!!.id
                        Toast.makeText(context, "id:${overweightId}", Toast.LENGTH_SHORT).show()
                        textViewCondition.apply {
                            visibility = View.VISIBLE
                            text = context.getString(R.string.obesity_and_overweight_weight)
                            setOnClickListener {
                                val action =
                                    ToolsFragmentDirections.actionNavigationToolsToConditionFragment(
                                        overweightId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }

                    else -> textViewCondition.visibility = View.INVISIBLE
                }
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