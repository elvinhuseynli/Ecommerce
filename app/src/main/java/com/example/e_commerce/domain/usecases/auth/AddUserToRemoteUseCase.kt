package com.example.e_commerce.domain.usecases.auth

import com.example.e_commerce.core_utils.PasswordHashingHelper
import com.example.e_commerce.data.models.SignupDataModel
import com.example.e_commerce.domain.repositories.SignupRepository
import java.util.Base64
import javax.inject.Inject

class AddUserToRemoteUseCase @Inject constructor(
    private val repository: SignupRepository
) {

    suspend operator fun invoke(
        userData: SignupDataModel,
        callback: (String) -> Unit
    ) {
        val mSalt: ByteArray = PasswordHashingHelper.generateSalt()
        val hashedPassword = PasswordHashingHelper.hashPassword(userData.password, mSalt) ?: ""
        val salt = Base64.getEncoder().encodeToString(mSalt)

        val mappedData = hashMapOf<String, String>()
        mappedData["emailAddress"] = userData.emailAddress
        mappedData["fullName"] = userData.fullName
        mappedData["password"] = hashedPassword
        mappedData["salt"] = salt

        repository.addUserToDatabase().add(mappedData)
            .addOnSuccessListener {
                callback.invoke("success")
            }.addOnFailureListener {
                callback.invoke("Something unexpected happened")
            }
    }

}