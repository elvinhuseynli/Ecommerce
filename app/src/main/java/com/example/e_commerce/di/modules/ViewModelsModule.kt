package com.example.e_commerce.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerce.di.annotations.ViewModelKey
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.auth.LoginViewModel
import com.example.e_commerce.ui.viewmodels.auth.SignupViewModel
import com.example.e_commerce.ui.viewmodels.main.CartProductViewModel
import com.example.e_commerce.ui.viewmodels.main.ChangePasswordViewModel
import com.example.e_commerce.ui.viewmodels.main.LocationViewModel
import com.example.e_commerce.ui.viewmodels.main.ProductViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    fun bindSignupViewModel(viewModel: SignupViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    fun bindChangePasswordViewModel(viewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    fun bindProductViewModel(viewModel: ProductViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CartProductViewModel::class)
    fun bindCartProductViewModel(viewModel: CartProductViewModel): ViewModel

}