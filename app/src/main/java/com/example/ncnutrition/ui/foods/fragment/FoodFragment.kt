package com.example.ncnutrition.ui.foods.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentFoodBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory

class FoodFragment : Fragment() {
    private val viewModel: FoodViewModel by activityViewModels {
        FoodViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    lateinit var food: Food


    private fun bind(food: Food) {
        binding.apply {
            foodName.text = food.food_name
        }
    }
    private  val navigationArgs:FoodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveFood(id).observe(this.viewLifecycleOwner){
            selectedFood->
            food = selectedFood
            bind(food)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}