package com.example.codingchallenge.di

import com.example.codingchallenge.model.repository.UserApi
import com.example.codingchallenge.model.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi): UserRepository {
        return UserRepository(api)
    }
}
