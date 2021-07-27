package celik.abdullah.myrecipeapp.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipesApi {
    @GET("search/")
    suspend fun searchRecipes(
        @Header("Authorization") token: String,
        @Query("page") page:Int,
        @Query("query") query:String) : ListOfRecipesResponse
}