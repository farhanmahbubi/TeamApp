package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.teamapp.database.AppDatabase
import com.example.teamapp.database.User
import com.example.teamapp.databinding.RegisterActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnDaftar.setOnClickListener {
            val githubUsername = binding.edtUsrgithub.text.toString()
            val email = binding.edtEmail.text.toString()
            val appUsername = binding.edtUsername.text.toString()
            val password = binding.edtPass.text.toString()

            if (githubUsername.isNotEmpty() && email.isNotEmpty() && appUsername.isNotEmpty() && password.isNotEmpty()) {
                val userDao = db.UserDao()
                val user = User(0, githubUsername, email, appUsername, password)

                // Jalankan operasi penyimpanan dengan Kotlin Coroutines
                GlobalScope.launch {
                    val useruId = userDao.insert(user)

                    if (useruId.isNotEmpty()) {
                        // Data berhasil disimpan
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        // Gagal menyimpan data
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "User Sudah Ada", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
        txtLoginListener()
    }

    private fun txtLoginListener() {
        binding.txtLoginsekarang.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
