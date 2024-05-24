package com.example.e_commerce.di.modules.main

import com.example.e_commerce.data.repositoriesImpl.auth.ProductRepositoryImpl
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.domain.repositories.main.ProductRepository
import dagger.Module
import dagger.Provides


@Module
object ProductModule {

    @Provides
    @FeatureScope
    fun provideRepositoryImpl(): ProductRepository {
        return ProductRepositoryImpl()
    }
}

@Module
interface ProductRepositoryModule{

}