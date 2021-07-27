package celik.abdullah.myrecipeapp.utils

import androidx.recyclerview.widget.DiffUtil
import celik.abdullah.myrecipeapp.model.Recipe

object Const {
    const val BASE_URL = "https://food2fork.ca/api/recipe/"
    const val STARTING_PAGE_INDEX = 1
    const val TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    const val PAGE_SIZE = 30

    val RECIPE_COMPARATOR = object: DiffUtil.ItemCallback<Recipe>(){
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem.recipePk == newItem.recipePk
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem == newItem
    }

}