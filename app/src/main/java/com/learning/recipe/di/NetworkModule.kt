package com.learning.recipe.di

import com.learning.recipe.utils.Constants
import com.learning.recipe.data.network.RecipeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRecipeApi(retrofit: Retrofit): RecipeApiService =
        retrofit.create(RecipeApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
        .addConverterFactory(gsonConverterFactory).build()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).build()

}