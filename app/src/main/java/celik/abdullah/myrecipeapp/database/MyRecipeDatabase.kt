package celik.abdullah.myrecipeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.model.RecipeRemoteKey

@Database(entities = [Recipe::class, RecipeRemoteKey::class], version=1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyRecipeDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
    abstract fun recipeRemoteKeysDao(): RecipeRemoteKeysDao
}