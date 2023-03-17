package com.example.ncnutrition.ui.foods.fragment

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
import com.example.ncnutrition.databinding.FragmentFoodsListBinding
import com.example.ncnutrition.ui.foods.adapter.FoodsAdapter
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class FoodsFragment : Fragment() {

    private val viewModel: FoodViewModel by activityViewModels {
        FoodViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private var _binding: FragmentFoodsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentFoodsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FoodsAdapter { food ->
            val action =
                FoodsFragmentDirections.actionFoodsFragmentToFoodFragment(food.id) // pass arg food.id bug
            this.findNavController().navigate(action)
        }
        // Set the adapter
        binding.recyclerView.adapter = adapter

        viewModel.allFoods.observe(this.viewLifecycleOwner) { foods ->
            foods.let {
                adapter.submitList(it)
            }
            if (foods.isEmpty()){
                Toast.makeText(this.context, "no foods", Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}