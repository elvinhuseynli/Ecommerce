package com.example.e_commerce.di.modules.main

import com.example.e_commerce.data.repositoriesImpl.auth.LocationRepositoryImpl
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.domain.repositories.main.LocationRepository
import dagger.Module
import dagger.Provides


@Module
object LocationModule {

    @Provides
    @FeatureScope
    fun provideRepositoryImpl(): LocationRepository {
        return LocationRepositoryImpl()
    }
}

@Module
interface LocationRepositoryModule {

}