package com.example.e_commerce.domain.usecases.auth

import android.app.Activity
import com.example.e_commerce.domain.repositories.auth.LoginRepository
import javax.inject.Inject

class GetUserFromLocalUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(activity: Activity): String? {
        val preferences = repository.getUserFromLocalStorage(activity)
        return if(preferences.contains("userId")) {
            preferences.getString("userId", null)
        } else {
            null
        }
    }
}