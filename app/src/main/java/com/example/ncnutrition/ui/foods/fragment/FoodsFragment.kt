package com.example.ncnutrition.ui.foods.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ncnutrition.data.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentFoodsListBinding
import com.example.ncnutrition.ui.foods.adapter.FoodsAdapter
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class FoodsFragment : Fragment() {
    private var _binding: FragmentFoodsListBinding? = null

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

        _binding = FragmentFoodsListBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FoodsAdapter {
//            val action =
        }
        // Set the adapter
        binding.recyclerView.adapter = adapter

        viewModel.allFoods.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

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