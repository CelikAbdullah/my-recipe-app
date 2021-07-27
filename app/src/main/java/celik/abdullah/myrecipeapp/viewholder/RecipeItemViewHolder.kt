package celik.abdullah.myrecipeapp.viewholder

import androidx.recyclerview.widget.RecyclerView
import celik.abdullah.myrecipeapp.R
import celik.abdullah.myrecipeapp.databinding.RecipeItemBinding
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.viewmodel.RecipesFragmentViewModel
import com.bumptech.glide.RequestManager

class RecipeItemViewHolder(private val binding: RecipeItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(recipeItem: Recipe?, recipesFragmentViewModel: RecipesFragmentViewModel, requestManager: RequestManager){
        if(recipeItem == null){
            binding.recipeTitle.text = itemView.resources.getString(R.string.loading)
        }
        else{
            binding.apply {
                this.recipe = recipeItem
                this.viewModel = recipesFragmentViewModel
                this.requestManager = requestManager
                executePendingBindings()
            }
        }
    }
}