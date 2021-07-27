package celik.abdullah.myrecipeapp.di

import android.content.Context
import androidx.room.Room
import celik.abdullah.myrecipeapp.database.MyRecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : MyRecipeDatabase {
        return Room.databaseBuilder(context.applicationContext,
            MyRecipeDatabase::class.java,
            "my-recipe-database")
            .fallbackToDestructiveMigration()
            .build()
    }

}