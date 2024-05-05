package com.example.e_commerce.di.components

import android.content.Context
import com.example.e_commerce.di.modules.ViewModelsModule
import com.example.e_commerce.di.modules.auth.LoginModule
import com.example.e_commerce.di.modules.auth.LoginRepositoryModule
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.ui.fragments.auth.LoginFragment
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    modules = [
        LoginModule::class,
        LoginRepositoryModule::class,
        ViewModelsModule::class,
    ]
)
interface AppComponent {

    fun inject(loginFragment: LoginFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}