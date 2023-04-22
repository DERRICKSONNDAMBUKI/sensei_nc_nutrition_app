package com.example.ncnutrition.ui.foods.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.R
import com.example.ncnutrition.databinding.FragmentFoodBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.time.ZoneId
import java.util.*

class FoodFragment : Fragment() {
    private val viewModel: FoodViewModel by activityViewModels {
        FoodViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    private val mealViewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    lateinit var food: Food
    private var selectedDate: Date = Date()

    //    bind to view
    private fun bind(food: Food) {
        val date = Date()
        binding.apply {
            food.apply {
                foodName.text = food.food_name
                calendarViewMealDate.minDate = date.time
                textViewEnergyInKJValue.text = food.energy_in_kJ.toString()
                textViewEnergyInKcalValue.text = food.energy_in_kcal.toString()
//            proximate
                textViewWaterInGValue.text = water_in_g.toString()
                textViewProteinInGValue.text = protein_in_g.toString()
                textViewFatInGValue.text = fat_in_g.toString()
                textViewCarbohydrateAvailableInGValue.text = carbohydrate_available_in_g.toString()
                textViewFibreIngValue.text = fibre_in_g.toString()
                textViewAshInGValue.text = ash_in_g.toString()

//            minerals
                textViewCaInMgValue.text = ca_in_mg.toString()
                textViewFeInMgValue.text = fe_in_mg.toString()
                textViewMgInMgValue.text = mg_in_mg.toString()
                textViewPInMgValue.text = p_in_mg.toString()
                textViewKInMgValue.text = k_in_mg.toString()
                textViewNaInMgValue.text = na_in_mg.toString()
                textViewZnInMgValue.text = zn_in_mg.toString()
                textViewSeInMgValue.text = se_in_mg.toString()

//            vitamins
                textViewVitARaeInMcgValue.text = vit_a_rae_in_mcg.toString()
                textViewVitAReInMcgValue.text = vit_a_re_in_mcg.toString()
                textViewRetinolInMcgValue.text = retinol_in_mcg.toString()
                textViewBetaCaroteneEquivalentInMcgValue.text =
                    beta_carotene_equivalent_in_mcg.toString()
                textViewThiaminInMcgValue.text = thiamin_in_mcg.toString()
                textViewRiboflavinInMcgValue.text = riboflavin_in_mcg.toString()
                textViewNiacinInMcgValue.text = niacin_in_mcg.toString()
                textViewDietaryFolateInMcgValue.text = dietary_folate_eq_in_mcg.toString()
                textViewFoodFolateInMcgValue.text = food_folate_in_mcg.toString()
                textViewVitB12InMcgValue.text = vit_b12_in_mcg.toString()
                textViewVitCInMcgValue.text = vit_c_in_mcg.toString()

//                cholesterol
                textViewCholesterolInMgValue.text = cholesterol_in_mg.toString()
            }
        }
    }

    private val navigationArgs: FoodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//val intentFoodCode = arguments?.getString("Code")

        val code: String = navigationArgs.code
        viewModel.retrieveFood(code).observe(this.viewLifecycleOwner) { selectedFood ->
            food = selectedFood
            bind(food)
        }

        val calendarDate = binding.calendarViewMealDate

        calendarDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Do something with the selected date

            // Create a calendar object and set it to the selected date
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            // Save the selected date to the public variable
            selectedDate = calendar.time
//            Toast.makeText(context,"Selected date $selectedDate",Toast.LENGTH_SHORT).show()
        }
        binding.takeButton.setOnClickListener {
            addNewCondition()
        }
    }

    private fun isEntryValid(): Boolean {

        return mealViewModel.isEntryValid(
            name = binding.editTextMealName.text.toString(),
//            date = Date(binding.calendarViewMealDate.date),
            date = selectedDate,
            food = food,
        )
    }

    private fun addNewCondition() {
        if (isEntryValid()) {
            val name = binding.editTextMealName.text.toString()
            val date = selectedDate
            val food = food
            mealViewModel.addNewMeal(
                name = name,
                date = date,
                food = food,
            )
//            Toast.makeText(
//                context,
//                "saved food for $name on  ${
//                    date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth
//                }.",
//                Toast.LENGTH_SHORT
//            ).show()

            Snackbar.make(
                binding.scrollviewLinearLayoutFood,
                "Saved food for $name on ${
                    date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth
                }. " +
                        "Check...",
                Snackbar.LENGTH_SHORT
            ).setAction("Meals") {
                val action = FoodFragmentDirections.actionFoodFragmentToNavigationMeals()
                findNavController().navigate(action)
            }.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.foreground_color))
                .setActionTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.background_color
                    )
                )
                .show()

            binding.editTextMealName.text.clear()

        } else {
            Toast.makeText(context, "invalid meal, e.g breakfast", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}