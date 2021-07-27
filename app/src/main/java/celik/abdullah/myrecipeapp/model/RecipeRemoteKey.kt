package celik.abdullah.myrecipeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="recipe_remote_keys")
data class RecipeRemoteKey(
    @PrimaryKey
    val recipeId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)