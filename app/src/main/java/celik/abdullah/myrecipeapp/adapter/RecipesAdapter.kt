package celik.abdullah.myrecipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import celik.abdullah.myrecipeapp.databinding.RecipeItemBinding
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.utils.Const.RECIPE_COMPARATOR
import celik.abdullah.myrecipeapp.viewholder.RecipeItemViewHolder
import celik.abdullah.myrecipeapp.viewmodel.RecipesFragmentViewModel
import com.bumptech.glide.RequestManager

class RecipesAdapter(private val recipesFragmentViewModel: RecipesFragmentViewModel,
                     private val requestManager: RequestManager) : PagingDataAdapter<Recipe, RecipeItemViewHolder>(RECIPE_COMPARATOR) {
    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) =
        holder.bind(getItem(position), recipesFragmentViewModel, requestManager)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder =
        RecipeItemViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}