package com.example.e_commerce.di.modules.main

import com.example.e_commerce.data.repositoriesImpl.auth.ChangePasswordRepositoryImpl
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.domain.repositories.main.ChangePasswordRepository
import dagger.Module
import dagger.Provides


@Module
object ChangePasswordModule {

    @Provides
    @FeatureScope
    fun provideRepositoryImpl(): ChangePasswordRepository {
        return ChangePasswordRepositoryImpl()
    }
}


@Module
interface ChangePasswordRepositoryModule {

}