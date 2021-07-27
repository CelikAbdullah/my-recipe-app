package celik.abdullah.myrecipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.repository.RecipesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecipesFragmentViewModel @Inject constructor(private val recipesRepository: RecipesRepositoryInterface): ViewModel(){

    fun searchRecipes(query: String) : Flow<PagingData<Recipe>> =
        recipesRepository.searchRecipes(query).cachedIn(viewModelScope)
}