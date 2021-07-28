package celik.abdullah.myrecipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.repository.RecipesRepositoryInterface
import celik.abdullah.speeching.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecipesFragmentViewModel @Inject constructor(private val recipesRepository: RecipesRepositoryInterface): ViewModel(){

    fun searchRecipes(query: String) : Flow<PagingData<Recipe>> =
        recipesRepository.searchRecipes(query).cachedIn(viewModelScope)

    private val _recipeClickedEvent = MutableLiveData<Event<Long>>()
    val recipeClickedEvent : LiveData<Event<Long>> = _recipeClickedEvent
    fun recipeClicked(id:Long){ _recipeClickedEvent.value = Event(id) }
}