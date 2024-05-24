package com.example.e_commerce.core_utils

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.R
import com.example.e_commerce.ui.adapters.CampaignsModel
import com.example.e_commerce.ui.adapters.ProductCategoryModel
import com.example.e_commerce.ui.adapters.StoriesModel
import org.aviran.cookiebar2.CookieBar
import java.net.URI
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64


val listOfItems = listOf<StoriesModel>(
    StoriesModel((R.drawable.campaign1),  "https://images.pexels.com/photos/264636/pexels-photo-264636.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    StoriesModel(R.drawable.campaign2,  "https://images.pexels.com/photos/2733918/pexels-photo-2733918.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    StoriesModel(R.drawable.campaign3,  "https://images.pexels.com/photos/2449665/pexels-photo-2449665.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
)

const val FRUITS_VEGGIES = "Fruits/Veggies"
const val SWEETS = "Sweets"
const val ALCOHOLS = "Alcohol"
const val DRINKS = "Drinks"
const val DRY_FOOD = "Dry food"
const val MEAT_GASTRONOMY = "Meat/Gastronomy"
const val DAIRY = "Dairy"
const val CANNED = "Canned Food"


val listOfProducts = arrayListOf<ProductCategoryModel>(
    ProductCategoryModel(R.drawable.fruits, FRUITS_VEGGIES),
    ProductCategoryModel(R.drawable.sweets, SWEETS),
    ProductCategoryModel(R.drawable.alcohol, ALCOHOLS),
    ProductCategoryModel(R.drawable.drinks, DRINKS),
    ProductCategoryModel(R.drawable.cereals, DRY_FOOD),
    ProductCategoryModel(R.drawable.meat, MEAT_GASTRONOMY),
    ProductCategoryModel(R.drawable.dairy, DAIRY),
    ProductCategoryModel(R.drawable.canned, CANNED),
)

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