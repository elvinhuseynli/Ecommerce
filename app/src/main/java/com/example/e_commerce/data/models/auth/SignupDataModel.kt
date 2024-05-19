package com.example.e_commerce.data.models.auth

data class SignupDataModel (
    val emailAddress: String,
    val fullName: String,
    val password: String,
    val salt: String
)