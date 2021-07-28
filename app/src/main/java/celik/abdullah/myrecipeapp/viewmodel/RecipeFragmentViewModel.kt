package celik.abdullah.myrecipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.repository.RecipesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeFragmentViewModel @Inject constructor(private val recipesRepository: RecipesRepositoryInterface): ViewModel() {

    private val _recipeEvent = MutableLiveData<Recipe>()
    val recipeEvent : LiveData<Recipe> = _recipeEvent

    fun getRecipeById(id:Long){
        viewModelScope.launch {
            _recipeEvent.value = recipesRepository.getRecipeById(id)
        }
    }
}