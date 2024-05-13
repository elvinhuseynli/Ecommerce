package com.example.e_commerce.di.components

import android.content.Context
import com.example.e_commerce.di.modules.ViewModelsModule
import com.example.e_commerce.di.modules.auth.LoginModule
import com.example.e_commerce.di.modules.auth.LoginRepositoryModule
import com.example.e_commerce.di.modules.auth.SignupModule
import com.example.e_commerce.di.modules.auth.SignupRepositoryModule
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.ui.activities.AuthActivity
import com.example.e_commerce.ui.fragments.auth.EmailValidationFragment
import com.example.e_commerce.ui.fragments.auth.LoginFragment
import com.example.e_commerce.ui.fragments.auth.SignupFragment
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    modules = [
        LoginModule::class,
        LoginRepositoryModule::class,
        SignupModule::class,
        SignupRepositoryModule::class,
        ViewModelsModule::class,
    ]
)
interface AppComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(signupFragment: SignupFragment)
    fun inject(authActivity: AuthActivity)
    fun inject(emailValidationFragment: EmailValidationFragment)


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}