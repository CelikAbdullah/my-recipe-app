package celik.abdullah.myrecipeapp.network

import celik.abdullah.myrecipeapp.model.Recipe
import com.google.gson.annotations.SerializedName

data class ListOfRecipesResponse(
    @field:SerializedName("count") val count: Long = 0,
    @field:SerializedName("next") val next: String? = null,
    @field:SerializedName("previous") val previous: String? = null,
    @field:SerializedName("results") val listOfRecipes: List<Recipe> = emptyList()
)