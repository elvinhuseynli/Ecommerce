package com.example.e_commerce.domain.usecases.auth

import android.app.Activity
import com.example.e_commerce.core_utils.PasswordHashingHelper
import com.example.e_commerce.domain.repositories.auth.LoginRepository
import java.util.Base64
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(
        activity: Activity,
        emailAddress: String,
        password: String,
        callback: (String) -> Unit
    ) {
        repository.getUserByEmail(emailAddress).addOnCompleteListener {
            if (it.isSuccessful) {
                if (!it.result.isEmpty) {
                    for (doc in it.result) {
                        val salt = doc.getString("salt")
                        val generatedSalt = Base64.getDecoder().decode(salt)

                        val hashedPass = PasswordHashingHelper.hashPassword(password, generatedSalt)
                        val retrievedPass = doc.getString("password")

                        if (!hashedPass.equals(retrievedPass)) {
                            callback.invoke("Wrong password")
                        } else {
                            repository.addUserToLocalStorage(activity,doc.id)
                            callback.invoke("success")
                        }
                    }
                } else {
                    callback.invoke("User doesn't exist")
                }
            } else {
                callback.invoke("Server error occurred")
            }
        }
    }

}