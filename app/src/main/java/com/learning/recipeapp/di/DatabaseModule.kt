package com.learning.recipeapp.di

import android.content.Context
import androidx.room.Room
import com.learning.recipeapp.data.database.RecipesDatabase
import com.learning.recipeapp.utils.Constants.DATABASE_NAME
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
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RecipesDatabase::class.java, name = DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(recipesDatabase: RecipesDatabase) = recipesDatabase.recipesDao()
}