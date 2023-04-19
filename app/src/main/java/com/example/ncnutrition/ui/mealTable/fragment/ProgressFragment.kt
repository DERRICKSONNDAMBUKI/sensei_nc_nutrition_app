package com.example.ncnutrition.ui.mealTable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentProgressBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.mealTable.viewModel.MealTotals
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import java.time.ZoneId
import java.util.*

class ProgressFragment : Fragment() {

    private val viewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private var selectedMealDate: Date = Date()
    lateinit var mealFoods: List<Food>
    lateinit var mealTotals: MealTotals

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarViewProgress = binding.calendarViewProgress

        val date = Date()
//        calendarViewProgress.minDate = minDate.time
        selectedMealDate = date
        calendarViewProgress.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            selectedMealDate = calendar.time
            selectDate(selectedMealDate)
        }

    }

    private fun selectDate(selectedMealDate: Date) {
        viewModel.getMealsBefore(selectedMealDate).observe(this.viewLifecycleOwner) {
            mealFoods = it
            if (mealFoods.isEmpty()) {
                Toast.makeText(
                    context,
                    "${mealFoods.count()} no meals on ${selectedMealDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth}",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                mealTotals = MealTotals(mealFoods)
                bindTotals(mealTotals)

//                binding.textViewProgress.text = mealFoods.toString()
                Toast.makeText(
                    context,
                    "${mealFoods.count()} meals by date ${
                        selectedMealDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun bindTotals(mealTotals: MealTotals) {
        binding.apply {
            //        energy
            textViewEnergyInKJ.text = "${mealTotals.energyInKJ} Kilojoules"
            textViewEnergyInKcal.text = "${mealTotals.energyInKcal} Kilocalories"

            //        proximate
            textViewWaterInG.text = "Water: ${mealTotals.waterInG} g"
            textViewProteinInG.text = "Protein: ${mealTotals.proteinInG} g"
            textViewFatInG.text = "Fat: ${mealTotals.fatInG} g"
            textViewCarbohydrateAvailableInG.text = "Carbohydrates available: ${mealTotals.carbohydrateAvailableInG} g"
            textViewFibreInG.text="Fibre: ${mealTotals.fibreInG} g"
            textViewAshInG.text ="Ash: ${mealTotals.ashInG} g"

   //        minerals
            textViewCaInMg.text = "Calcium: ${mealTotals.caInMg} mg"
            textViewFeInMg.text = "Iron: ${mealTotals.feInMg} mg"
            textViewMgInMg.text = "Magnesium: ${mealTotals.mgInMg} mg"
            textViewPInMg.text = "Phosphorus: ${mealTotals.pInMg} mg"
            textViewKInMg.text = "Potassium: ${mealTotals.kInMg} mg"
            textViewNaInMg.text = "Sodium: ${mealTotals.naInMg} mg"
            textViewZnInMg.text = "Zinc: ${mealTotals.znInMg} mg"
            textViewSeInMg.text = "Selenium: ${mealTotals.seInMg} mg"

    //        vitamins
            textViewVitARaeInMcg.text  ="Vitamin A RAE: ${mealTotals.vitARaeInMcg} mcg"
            textViewVitAReInMcg.text ="Vitamin A RE: ${mealTotals.vitAReInMcg} mcg"
            textViewRetinolInMcg.text = "Retinol: ${mealTotals.retinolInMcg} mcg"
            textViewBetaCaroteneEquivalentInMcg.text = "beta-Carotene: ${mealTotals.betaCaroteneEquivalentInMcg} mcg"
            textViewThiaminInMcg.text ="Thiamin: ${mealTotals.thiaminInMcg} mcg"
            textViewRiboflavinInMcg.text ="Riboflavin: ${mealTotals.riboflavinInMcg} mcg"
            textViewNiacinInMcg.text = "Niacin: ${mealTotals.niacinInMcg}  mcg"
            textViewDietaryFolateInMcg.text = "Dietary: ${mealTotals.dietaryFolateEqInMcg} mcg"
            textViewFoodFolateInMcg.text ="Food folate: ${mealTotals.foodFolateInMcg} mcg"
            textViewVitB12InMcg.text ="Vitamin B12: ${mealTotals.vitB12InMcg} mcg"
            textViewVitCInMcg.text = "Vitamin C: ${mealTotals.vitCInMcg} mcg"

    //        cholesterol
            textViewCholesterolInMg.text = "Cholesterol ${mealTotals.cholesterolInMg} mg"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}