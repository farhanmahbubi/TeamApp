package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.teamapp.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.teamapp.databinding.LoginActivityBinding


class LoginActivity : ComponentActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnMasuk.setOnClickListener {
            val appUsername = binding.edtUser.text.toString()
            val password = binding.edtPass.text.toString()

            if (appUsername.isNotEmpty() && password.isNotEmpty()) {
                val userDao = db.UserDao()

                // Jalankan operasi login dengan Kotlin Coroutines
                GlobalScope.launch {
                    val user = userDao.checkUserPass(appUsername, password)

                    if (user.isNotEmpty()) {
                        // Login berhasil, simpan status login
                        saveLoginStatus()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        // Gagal login
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Login Gagal. Cek kembali username dan password.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@LoginActivity, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
        txtDaftarListener()
    }

    private fun txtDaftarListener() {
        binding.txtDaftardulu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Fungsi untuk menyimpan status login
    private fun saveLoginStatus() {
        val sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", true)
        editor.apply()
    }
}
