package com.example.ncnutrition.ui.recipes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentRecipeBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.recipes.viewModel.RecipeViewModel
import com.example.ncnutrition.ui.recipes.viewModel.RecipeViewModelFactory

class RecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    lateinit var recipe: Food

    private fun bind(recipe: Food) {
        binding.apply {
            textViewRecipeName.text = recipe.food_name
            textViewDishDescription.text = recipe.food_description
            textViewDishTimeP.text = recipe.dish_time
            textViewIngredients.text = recipe.food_ingredients
            textViewDirections.text = recipe.food_preparation_cooking_serves_makes

        }
    }

    private val navigationArgs: RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val code = navigationArgs.code

        viewModel.retrieveRecipe(code).observe(this.viewLifecycleOwner){ selectedRecipe->
            recipe = selectedRecipe
            bind(recipe)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

