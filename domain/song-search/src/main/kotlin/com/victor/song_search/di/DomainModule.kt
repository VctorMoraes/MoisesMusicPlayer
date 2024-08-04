package com.victor.song_search.di

import com.victor.song_search.SearchSongUseCase
import com.victor.song_search.SearchSongUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindsSearchSongUseCase(
        searchSongUseCase: SearchSongUseCaseImpl
    ): SearchSongUseCase

}