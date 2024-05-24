package com.example.e_commerce.di.components

import android.content.Context
import com.example.e_commerce.di.modules.ViewModelsModule
import com.example.e_commerce.di.modules.auth.LoginModule
import com.example.e_commerce.di.modules.auth.LoginRepositoryModule
import com.example.e_commerce.di.modules.auth.SignupModule
import com.example.e_commerce.di.modules.auth.SignupRepositoryModule
import com.example.e_commerce.di.modules.main.ChangePasswordModule
import com.example.e_commerce.di.modules.main.ChangePasswordRepositoryModule
import com.example.e_commerce.di.modules.main.LocationModule
import com.example.e_commerce.di.modules.main.LocationRepositoryModule
import com.example.e_commerce.di.modules.main.ProductModule
import com.example.e_commerce.di.modules.main.ProductRepositoryModule
import com.example.e_commerce.di.scopes.FeatureScope
import com.example.e_commerce.ui.activities.AuthActivity
import com.example.e_commerce.ui.fragments.auth.EmailValidationFragment
import com.example.e_commerce.ui.fragments.auth.LoginFragment
import com.example.e_commerce.ui.fragments.auth.SignupFragment
import com.example.e_commerce.ui.fragments.main.locations.LocationsFragment
import com.example.e_commerce.ui.fragments.main.products.AllProductsFragment
import com.example.e_commerce.ui.fragments.main.products.ProductsFragment
import com.example.e_commerce.ui.fragments.main.products.SelectedCategoryProductsFragment
import com.example.e_commerce.ui.fragments.main.profile.ChangePasswordFragment
import com.example.e_commerce.ui.fragments.main.profile.FavoriteProductsFragment
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    modules = [
        LoginModule::class,
        LoginRepositoryModule::class,
        SignupModule::class,
        SignupRepositoryModule::class,
        ChangePasswordModule::class,
        ChangePasswordRepositoryModule::class,
        ProductModule::class,
        ProductRepositoryModule::class,
        LocationModule::class,
        LocationRepositoryModule::class,
        ViewModelsModule::class,
    ]
)
interface AppComponent {

    fun inject(loginFragment: LoginFragment)

    fun inject(signupFragment: SignupFragment)
    fun inject(authActivity: AuthActivity)
    fun inject(emailValidationFragment: EmailValidationFragment)

    fun inject(changePasswordFragment: ChangePasswordFragment)
    fun inject(productsFragment: SelectedCategoryProductsFragment)

    fun inject(allProductsFragment: AllProductsFragment)

    fun inject(favoriteProductsFragment: FavoriteProductsFragment)

    fun inject(locationsFragment: LocationsFragment)


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}