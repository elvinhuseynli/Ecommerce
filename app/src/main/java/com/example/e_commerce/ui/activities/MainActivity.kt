package com.example.e_commerce.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNav = binding.bottomNavigation
        setupWithNavController(bottomNav, navController)

        bottomNav.setOnItemSelectedListener { item ->
            val list = listOf(
                R.id.home_nav_graph,
                R.id.products_nav_graph,
                R.id.campaigns_nav_graph,
                R.id.locations_nav_graph,
                R.id.profile_nav_graph
            )
            if(item.itemId in list) {
                navController.navigate(item.itemId, null,
                    NavOptions.Builder().setPopUpTo(item.itemId, true).build())
                true
            } else {
                false
            }

        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == R.id.homeFragment || !navController.popBackStack()) {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        })
    }
}