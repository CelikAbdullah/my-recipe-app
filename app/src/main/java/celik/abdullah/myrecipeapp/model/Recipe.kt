package celik.abdullah.myrecipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="recipes")
data class Recipe(
    @PrimaryKey
    @ColumnInfo(name="recipe_pk")
    @field:SerializedName("pk")
    val recipePk: Long,
    @ColumnInfo(name="recipe_title")
    @field:SerializedName("title")
    val recipeTitle: String,
    @ColumnInfo(name="recipe_image_url")
    @field:SerializedName("featured_image")
    val recipeImageUrl: String,
    @ColumnInfo(name = "recipe_rating")
    @field:SerializedName("rating")
    val recipeRating: Long,
    @ColumnInfo(name="recipe_ingredients")
    @field:SerializedName("ingredients")
    val recipeIngredients: ArrayList<String>
)
