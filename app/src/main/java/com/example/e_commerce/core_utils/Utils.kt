package com.example.e_commerce.core_utils

import android.annotation.SuppressLint
import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.R
import org.aviran.cookiebar2.CookieBar
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64


val Fragment.viewLifecycleScope: LifecycleCoroutineScope
    get() = this.viewLifecycleOwner.lifecycleScope

class CookieBarInst {
    companion object {

        fun buildMessage(activity: Activity, msg: String) {
            CookieBar.build(activity)
                .setTitleColor(R.color.white)
                .setCustomView(R.layout.custom_toast)
                .setMessage(msg)
                .setIcon(R.drawable.warning_vector)
                .setCookiePosition(CookieBar.TOP)
                .setDuration(2000)
                .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                .show()
        }

    }
}

class PasswordHashingHelper {
    companion object {

        fun generateSalt(): ByteArray {
            val random = SecureRandom()
            val salt = ByteArray(16)
            random.nextBytes(salt)
            return salt
        }

        @SuppressLint("NewApi")
        fun hashPassword(password: String, salt: ByteArray): String? {
            return try {
                val digest = MessageDigest.getInstance("SHA-256")
                digest.update(salt)
                val hashedPassword = digest.digest(password.toByteArray())
                Base64.getEncoder().encodeToString(hashedPassword)
            } catch (e: NoSuchAlgorithmException) {
                null
            }
        }
    }
}