package com.example.ncnutrition.ui.foods.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.data.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentFoodsBinding
import com.example.ncnutrition.ui.foods.adapter.FoodsAdapter
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class FoodsFragment : Fragment() {
    private var _binding: FragmentFoodsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FoodViewModel by activityViewModels {
        FoodViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        // Set the adapter
        _binding = FragmentFoodsBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FoodsAdapter {

        }
//        TODO("binding.recyclerView.adapter = adapter")

        viewModel.allFoods.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        TODO(
            "binding.recyclerView.layoutManager = LinearLayoutManager(this.context)\n" +
                    "   binding.floatingActionButton.setOnClickListener {\n" +
                    "       val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(\n" +
                    "           getString(R.string.add_fragment_title)\n" +
                    "       )\n" +
                    "       this.findNavController().navigate(action)\n" +
                    "   }\n"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}