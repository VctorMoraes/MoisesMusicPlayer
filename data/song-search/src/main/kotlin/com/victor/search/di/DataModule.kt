package com.victor.search.di

import com.victor.search.datasource.remote.api.SearchApi
import com.victor.search.repository.SearchRepository
import com.victor.search.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsSearchRepository(
        loanRequestRepository: SearchRepositoryImpl
    ): SearchRepository

    companion object {

        @Singleton
        @Provides
        fun providesSearchApi(retrofit: Retrofit): SearchApi = retrofit.create(
            SearchApi::class.java
        )
    }
}