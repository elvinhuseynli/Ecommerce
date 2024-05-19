package com.example.e_commerce.di.modules.auth

import com.example.e_commerce.data.repositoriesImpl.auth.LoginRepositoryImpl
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.domain.repositories.LoginRepository
import dagger.Module
import dagger.Provides


@Module
object LoginModule {

    @Provides
    @FeatureScope
    fun provideRepositoryImpl(): LoginRepository {
        return LoginRepositoryImpl()
    }
}

@Module
interface LoginRepositoryModule {

}