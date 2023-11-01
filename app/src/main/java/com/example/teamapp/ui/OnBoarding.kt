package com.example.teamapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.R
import com.example.teamapp.databinding.FragmentOnBoardingBinding
import com.example.teamapp.databinding.LoginActivityBinding

class OnBoarding : AppCompatActivity() {
    private lateinit var binding: FragmentOnBoardingBinding
//    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        // Initialize SharedPreferences
//        sharedPreferences = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
//
//        // Check if onboarding has already been shown
//        if (sharedPreferences.getBoolean("isFirstRun", true)) {
//            // Onboarding has not been shown, navigate to onboarding
//            with(sharedPreferences.edit()) {
//                putBoolean("isFirstRun", false)
//                apply()
//            }
//        } else {
//            // Onboarding has already been shown, navigate to login activity
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish() // Finish current activity to prevent returning to onboarding
//        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finish current activity after navigating to LoginActivity
        }

        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}