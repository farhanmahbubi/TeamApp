package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
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
                        val userId = user[0].id
                        val username = user[0].appUsername
                        val email = user[0].email
                        saveUserData(userId, username, email)

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
    // Simpan SharedPreferences saat login
    private fun saveLoginStatus() {
        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", true)
        editor.apply()
    }

    // Fungsi untuk menyimpan data pengguna ke SharedPreferences
    private fun saveUserData(userId: Int, username: String?, email: String?) {
        val sharedPreferences = getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Simpan data pengguna
        editor.putInt("user_id", userId)
        editor.putString("username", username)
        editor.putString("email", email)
        editor.apply()
    }
}

