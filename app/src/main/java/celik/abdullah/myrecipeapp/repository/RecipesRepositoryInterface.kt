package celik.abdullah.myrecipeapp.repository

import androidx.paging.PagingData
import celik.abdullah.myrecipeapp.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepositoryInterface {
    fun searchRecipes(query:String): Flow<PagingData<Recipe>>
}