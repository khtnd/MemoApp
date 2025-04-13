package com.example.memoapp.di

import com.example.memoapp.repository.DefaultTextRepository
import com.example.memoapp.repository.TextRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindTextRepository(impl: DefaultTextRepository): TextRepository

}