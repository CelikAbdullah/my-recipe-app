package celik.abdullah.myrecipeapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import celik.abdullah.myrecipeapp.model.RecipeRemoteKey

@Dao
interface RecipeRemoteKeysDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertRecipeRemoteKeys(remoteKeys: List<RecipeRemoteKey>)

    @Query("SELECT * FROM recipe_remote_keys WHERE recipeId=:recipeId")
    suspend fun queryRecipeRemoteKeyById(recipeId: Long): RecipeRemoteKey?

    @Query("DELETE FROM recipe_remote_keys")
    suspend fun clearRecipeRemoteKeys()
}