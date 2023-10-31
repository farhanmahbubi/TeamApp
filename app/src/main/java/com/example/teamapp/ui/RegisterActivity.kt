package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.teamapp.database.AppDatabase
import com.example.teamapp.database.User
import com.example.teamapp.databinding.RegisterActivityBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: RegisterActivityBinding
    lateinit var auth : FirebaseAuth
//    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.txtLoginsekarang.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnDaftar.setOnClickListener {

            val username = binding.edtUsername.text.toString()
            val password = binding.edtPass.text.toString()
            val emailgit = binding.edtUsrgithub.text.toString()
            val email = binding.edtEmail.text.toString()

            // Validasi email
            if (username.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                binding.edtUsername.error = if (username.isEmpty()) "Email Harus Diisi" else "Email Tidak Valid"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }

            // Validasi password
            if (password.isEmpty() || password.length < 6) {
                binding.edtPass.error = if (password.isEmpty()) "Password Harus Diisi" else "Password Minimal 6 Karakter"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            // Validasi emailgit dan email
            if (emailgit.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailgit).matches() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fullEmail = "$emailgit@$email" // Menggabungkan emailgit dan email

            RegisterFirebase(fullEmail, password)
        }
    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

