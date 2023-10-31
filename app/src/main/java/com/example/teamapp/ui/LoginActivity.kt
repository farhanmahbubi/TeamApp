package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.teamapp.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : ComponentActivity() {
    private lateinit var binding: LoginActivityBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.txtDaftardulu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnMasuk.setOnClickListener {
            val email = binding.edtUser.text.toString()
            val password = binding.edtPass.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.edtUser.error = "Email Harus Diisi"
                binding.edtUser.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtPass.error = "Email Tidak Valid"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.edtPass.error = "Password Harus Diisi"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email,password)
        }
    }
    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

