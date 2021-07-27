package celik.abdullah.myrecipeapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import celik.abdullah.myrecipeapp.database.MyRecipeDatabase
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.network.RecipesApi
import celik.abdullah.myrecipeapp.remotemediator.SearchRecipesRemoteMediator
import celik.abdullah.myrecipeapp.utils.Const.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class RecipesRepository @Inject constructor(private val recipesApi: RecipesApi,
                                            private val myRecipeDatabase: MyRecipeDatabase) : RecipesRepositoryInterface {
    override fun searchRecipes(query:String): Flow<PagingData<Recipe>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = SearchRecipesRemoteMediator(recipesApi, myRecipeDatabase, query),
            pagingSourceFactory = {myRecipeDatabase.recipesDao().queryRecipes("%${query.replace(' ', '%')}%")}
        ).flow
}