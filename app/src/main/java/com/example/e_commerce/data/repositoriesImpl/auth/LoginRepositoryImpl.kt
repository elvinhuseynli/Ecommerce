package com.example.e_commerce.data.repositoriesImpl.auth

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.e_commerce.domain.repositories.auth.LoginRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class LoginRepositoryImpl : LoginRepository {

    override suspend fun getUserByEmail(emailAddress: String): Task<QuerySnapshot> {
        return Firebase.firestore
            .collection("users")
            .whereEqualTo("emailAddress", emailAddress)
            .get()
    }

    override fun getUserFromLocalStorage(activity: Activity): SharedPreferences {
        return activity.getSharedPreferences("app", Context.MODE_PRIVATE)
    }

    override fun addUserToLocalStorage(activity: Activity, userId: String) {
        val sharedPreferences = activity.getSharedPreferences("app", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.apply()
    }

}