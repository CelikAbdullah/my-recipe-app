package celik.abdullah.myrecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import celik.abdullah.myrecipeapp.databinding.FragmentRecipeBinding
import celik.abdullah.myrecipeapp.viewmodel.RecipeFragmentViewModel
import celik.abdullah.myrecipeapp.viewmodel.SharedViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val recipeFragmentViewModel by viewModels<RecipeFragmentViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    @Inject
    lateinit var rm: RequestManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)

        binding.apply{
            lifecycleOwner = viewLifecycleOwner
            requestManager = rm
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        sharedViewModel.currentRecipeId.observe(viewLifecycleOwner, Observer{ recipeId->
            recipeFragmentViewModel.getRecipeById(recipeId)
        })

        recipeFragmentViewModel.recipeEvent.observe(viewLifecycleOwner, Observer{ recipe->
            binding.recipe = recipe
            val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, recipe.recipeIngredients)
            binding.ingredients.adapter = itemsAdapter
        })
    }
}