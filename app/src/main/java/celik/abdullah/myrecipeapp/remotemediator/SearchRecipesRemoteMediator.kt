package celik.abdullah.myrecipeapp.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import celik.abdullah.myrecipeapp.database.MyRecipeDatabase
import celik.abdullah.myrecipeapp.model.Recipe
import celik.abdullah.myrecipeapp.model.RecipeRemoteKey
import celik.abdullah.myrecipeapp.network.RecipesApi
import celik.abdullah.myrecipeapp.utils.Const.STARTING_PAGE_INDEX
import celik.abdullah.myrecipeapp.utils.Const.TOKEN
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class SearchRecipesRemoteMediator(private val recipesApi: RecipesApi, private val myRecipeDatabase: MyRecipeDatabase, private val query: String, ) : RemoteMediator<Int, Recipe>() {

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Recipe>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val apiResponse = recipesApi.searchRecipes(token=TOKEN, page=page, query=query)

            val recipes = apiResponse.listOfRecipes
            val endOfPaginationReached = recipes.isEmpty()
            myRecipeDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    myRecipeDatabase.recipeRemoteKeysDao().clearRecipeRemoteKeys()
                    myRecipeDatabase.recipesDao().clearRecipes()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = recipes.map {
                    RecipeRemoteKey(recipeId = it.recipePk, prevKey = prevKey, nextKey = nextKey)
                }
                myRecipeDatabase.recipeRemoteKeysDao().insertRecipeRemoteKeys(keys)
                myRecipeDatabase.recipesDao().insertRecipes(recipes)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Recipe>): RecipeRemoteKey? =
        state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { recipe -> myRecipeDatabase.recipeRemoteKeysDao().queryRecipeRemoteKeyById(recipe.recipePk) }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Recipe>): RecipeRemoteKey? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { recipe ->  myRecipeDatabase.recipeRemoteKeysDao().queryRecipeRemoteKeyById(recipe.recipePk) }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Recipe>): RecipeRemoteKey? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.recipePk?.let { recipeId ->
                myRecipeDatabase.recipeRemoteKeysDao().queryRecipeRemoteKeyById(recipeId)
            }
        }
}