package celik.abdullah.myrecipeapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import celik.abdullah.myrecipeapp.R
import celik.abdullah.myrecipeapp.database.MyRecipeDatabase
import celik.abdullah.myrecipeapp.network.RecipesApi
import celik.abdullah.myrecipeapp.repository.RecipesRepository
import celik.abdullah.myrecipeapp.repository.RecipesRepositoryInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions = RequestOptions().placeholder(R.drawable.white_background).error(R.drawable.white_background)

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context, requestOptions: RequestOptions) : RequestManager =
        Glide.with(context.applicationContext).setDefaultRequestOptions(requestOptions)

    @Singleton
    @Provides
    fun provideRecipesRepository(recipesApi: RecipesApi, myRecipeDatabase: MyRecipeDatabase): RecipesRepositoryInterface = RecipesRepository(recipesApi, myRecipeDatabase)
}