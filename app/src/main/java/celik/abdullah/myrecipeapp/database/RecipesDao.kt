package celik.abdullah.myrecipeapp.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import celik.abdullah.myrecipeapp.model.Recipe

@Dao
interface RecipesDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes:List<Recipe>)

    @Query("SELECT * FROM recipes WHERE recipe_title LIKE :query ORDER BY recipe_title ASC")
    fun queryRecipes(query:String): PagingSource<Int, Recipe>

    @Query("DELETE FROM recipes")
    suspend fun clearRecipes()
}