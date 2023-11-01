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
//        db = AppDatabase.getDatabase(this)

        binding.txtLoginsekarang.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnDaftar.setOnClickListener {

            val email = binding.edtUsername.text.toString()
            val password = binding.edtPass.text.toString()

            //Validasi email
            if (email.isEmpty()) {
                binding.edtUsername.error = "Email Harus Diisi"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtUsername.error = "Email Tidak Valid"
                binding.edtUsername.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()) {
                binding.edtPass.error = "Password Harus Diisi"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            //Validasi panjang password
            if (password.length < 6) {
                binding.edtPass.error = "Password Minimal 6 Karakter"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email, password)
        }
    }

//            val githubUsername = binding.edtUsrgithub.text.toString()
//            val email = binding.edtEmail.text.toString()
//            val appUsername = binding.edtUsername.text.toString()
//            val password = binding.edtPass.text.toString()

//            if (githubUsername.isNotEmpty() && email.isNotEmpty() && appUsername.isNotEmpty() && password.isNotEmpty()) {
//                val userDao = db.UserDao()
//                val user = User(0, githubUsername, email, appUsername, password)
//
//                // Jalankan operasi penyimpanan dengan Kotlin Coroutines
//                GlobalScope.launch {
//                    val useruId = userDao.insert(user)
//
//                    if (useruId.isNotEmpty()) {
//                        // Data berhasil disimpan
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                            startActivity(intent)
//                        }
//                    } else {
//                        // Gagal menyimpan data
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(this@RegisterActivity, "User Sudah Ada", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            } else {
//                Toast.makeText(this@RegisterActivity, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
//            }

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

