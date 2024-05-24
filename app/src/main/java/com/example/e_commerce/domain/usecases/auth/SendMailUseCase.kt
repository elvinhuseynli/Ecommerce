package com.example.e_commerce.domain.usecases.auth

import com.example.e_commerce.core_utils.MailAPI

class SendMailUseCase {
    operator fun invoke(
        emailAddress: String,
        callback: (String)->Unit
    ) {

        val subjectOfMail = "OTP Code"
        val message = "Your OTP code is "
        val otpCode = ((Math.random() * 9000 + 1000).toInt()).toString()


        val api = MailAPI(emailAddress, subjectOfMail, message, otpCode)
        api.execute()

        callback.invoke(otpCode)

    }
}