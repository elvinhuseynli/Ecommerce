package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.core_utils.PasswordHashingHelper
import com.example.e_commerce.domain.repositories.main.ChangePasswordRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import java.util.Base64
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: ChangePasswordRepository
) {
    suspend operator fun invoke(
        userId: String,
        password: String,
        callback: (String)->Unit
    ) {
        repository.getUserById(userId).addOnCompleteListener{
            if(it.isSuccessful) {
                if(!it.result.isEmpty) {
                    for(doc in it.result) {
                        val salt = doc.getString("salt")
                        val generatedSalt = Base64.getDecoder().decode(salt)

                        val hashedPass = PasswordHashingHelper.hashPassword(password, generatedSalt)
                        val data = hashMapOf("password" to hashedPass)

                        Firebase.firestore
                            .collection("users")
                            .document(userId)
                            .set(data, SetOptions.merge()).addOnSuccessListener {
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