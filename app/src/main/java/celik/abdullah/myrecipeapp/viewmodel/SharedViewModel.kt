package celik.abdullah.myrecipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor(): ViewModel() {

    private val _currentRecipeId = MutableLiveData<Long>()
    val currentRecipeId : LiveData<Long> = _currentRecipeId
    fun currentRecipe(id:Long){_currentRecipeId.value = id }
}