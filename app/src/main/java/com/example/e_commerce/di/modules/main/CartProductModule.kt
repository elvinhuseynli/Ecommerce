package com.example.e_commerce.di.modules.main

import com.example.e_commerce.data.repositoriesImpl.main.CartProductsRepositoryImpl
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.domain.repositories.main.CartProductRepository
import dagger.Module
import dagger.Provides


@Module
object CartProductModule {

    @Provides
    @FeatureScope
    fun provideRepositoryImpl(): CartProductRepository {
        return CartProductsRepositoryImpl()
    }
}

@Module
interface CartProductRepositoryModule {

}