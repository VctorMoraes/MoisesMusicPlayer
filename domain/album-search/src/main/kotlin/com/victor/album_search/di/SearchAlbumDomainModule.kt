package com.victor.album_search.di

import com.victor.album_search.SearchAlbumByIdUseCase
import com.victor.album_search.SearchAlbumByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface SearchAlbumDomainModule {
    @Binds
    fun bindsSearchAlbumByIdUseCase(
        searchAlbumByIdUseCase: SearchAlbumByIdUseCaseImpl
    ): SearchAlbumByIdUseCase

}