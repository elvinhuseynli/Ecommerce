package com.example.e_commerce.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.MainApplication
import com.example.e_commerce.databinding.ActivityAuthBinding
import com.example.e_commerce.ui.intents.auth.LoginUIEvent
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.auth.LoginViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        (application as? MainApplication)?.getComponent()?.inject(this)

        handleUserDirectLogin()

        setContentView(binding.root)

    }

    private fun handleUserDirectLogin() {
        viewModel.uiState.onSubscription {
            viewModel.onEvent(LoginUIEvent.OnCreate(this@AuthActivity))
        }.onEach{

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (it.userId != "") {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    intent.putExtra("userId", it.userId)
                    startActivity(intent)
                }
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
        }.launchIn(lifecycleScope)
    }

}