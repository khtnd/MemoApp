package com.example.memoapp.di

import com.example.memoapp.repository.LocalTextRepository
import com.example.memoapp.repository.TextRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindTextRepository(impl: LocalTextRepository): TextRepository

}