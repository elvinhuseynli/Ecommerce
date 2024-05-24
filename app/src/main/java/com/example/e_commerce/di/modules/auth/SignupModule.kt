package com.example.e_commerce.di.modules.auth

import com.example.e_commerce.data.repositoriesImpl.auth.SignupRepositoryImpl
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.domain.repositories.auth.SignupRepository
import com.example.e_commerce.domain.usecases.auth.SendMailUseCase
import dagger.Module
import dagger.Provides


@Module
object SignupModule {

    @Provides
    @FeatureScope
    fun provideRepositoryImpl(): SignupRepository {
        return SignupRepositoryImpl()
    }

    @Provides
    @FeatureScope
    fun provideUseCase(): SendMailUseCase {
        return SendMailUseCase()
    }
}

@Module
interface SignupRepositoryModule {

}