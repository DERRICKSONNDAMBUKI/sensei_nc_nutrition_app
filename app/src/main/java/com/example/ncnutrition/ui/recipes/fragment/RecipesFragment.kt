package com.example.ncnutrition.ui.recipes.fragment

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
import com.example.ncnutrition.databinding.FragmentRecipesListBinding
import com.example.ncnutrition.ui.recipes.adapter.RecipesAdapter
import com.example.ncnutrition.ui.recipes.viewModel.RecipeViewModel
import com.example.ncnutrition.ui.recipes.viewModel.RecipeViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class RecipesFragment : Fragment() {
    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewRecipes = binding.recyclerViewRecipes
        // Set the adapter
        val adapter = RecipesAdapter { foods ->
            val action =
                RecipesFragmentDirections.actionNavigationRecipesToRecipeFragment(foods.code)
            this.findNavController().navigate(action)
        }
        recyclerViewRecipes.adapter = adapter
        viewModel.allRecipes().observe(this.viewLifecycleOwner) { foods ->
            foods.let {
                adapter.submitList(it)
            }
            if (foods.isNullOrEmpty()) {
                Toast.makeText(this.context, "no recipes", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerViewRecipes.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}