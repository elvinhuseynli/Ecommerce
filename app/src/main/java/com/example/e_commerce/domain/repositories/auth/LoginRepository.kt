package com.example.e_commerce.domain.repositories.auth

import android.app.Activity
import android.content.SharedPreferences
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface LoginRepository {

    suspend fun getUserByEmail(emailAddress: String): Task<QuerySnapshot>

    fun getUserFromLocalStorage(activity: Activity): SharedPreferences

    fun addUserToLocalStorage(activity: Activity, userId: String)
}
