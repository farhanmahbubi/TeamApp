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

    companion object {
        private var isFirstRun = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isFirstRun) {
            // First run, show onboarding
            isFirstRun = false
        } else {
            // Not the first run, go directly to Login
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finish current activity to prevent returning to onboarding
        }

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