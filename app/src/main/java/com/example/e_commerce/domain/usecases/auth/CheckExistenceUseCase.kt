package com.example.e_commerce.domain.usecases.auth

import com.example.e_commerce.domain.repositories.LoginRepository
import javax.inject.Inject

class CheckExistenceUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(
        emailAddress: String,
        callback: (String)->Unit
    ) {

        repository.getUserByEmail(emailAddress).addOnCompleteListener {
            if(it.isSuccessful) {
                if(it.result.isEmpty) {
                    callback.invoke("success")
                } else {
                    callback.invoke("User already exists")
                }
            } else {
                callback.invoke("Server error occurred")
            }
        }
    }

}