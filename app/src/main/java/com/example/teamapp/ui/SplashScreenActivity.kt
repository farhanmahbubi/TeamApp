package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import com.example.teamapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : ComponentActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            if (isLoggedIn) {
                // Pengguna telah login, arahkan ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Pengguna belum login, arahkan ke LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 3000)
    }
}
