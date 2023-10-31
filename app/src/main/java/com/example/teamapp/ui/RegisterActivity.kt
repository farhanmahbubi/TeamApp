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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

        binding.txtLoginsekarang.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnDaftar.setOnClickListener {
            val usernameGithub = binding.edtUsrgithub.text.toString()
            val email = binding.edtEmail.text.toString()
            val username = binding.edtUsername.text.toString()
            val nim = binding.edtNim.text.toString()
            val password = binding.edtPass.text.toString()

            if (usernameGithub.isEmpty() || email.isEmpty() || username.isEmpty() || nim.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
            } else {
                // Daftar pengguna ke Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            // Registrasi Firebase Authentication berhasil
                            val userId = auth.currentUser?.uid ?: ""
                            val dataUser = DataUser(usernameGithub, email, username, nim, password)
                            // Simpan data pengguna ke Firebase Realtime Database
                            database.child("Users").child(userId).setValue(dataUser).addOnSuccessListener {
                                Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }.addOnFailureListener {
                                Toast.makeText(this, "Gagal disimpan", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Gagal mendaftar dengan Firebase Authentication
                            Toast.makeText(this, "Gagal mendaftar", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}



//    private lateinit var db: AppDatabase


//db = AppDatabase.getDatabase(this)
//Validasi usernameGithub
//            if(usernameGithub.isEmpty()) {
//                binding.edtUsrgithub.error = "Username Github Harus Diisi"
//                binding.edtUsrgithub.requestFocus()
//                return@setOnClickListener
//            }
//
//            //Validasi usernamegithub tidak sesuai
//            if(!Patterns.DOMAIN_NAME.matcher(usernameGithub).matches()) {
//                binding.edtUsrgithub.error = "Username Github Tidak Valid"
//                binding.edtUsrgithub.requestFocus()
//                return@setOnClickListener
//            }
//
//            //Validasi email
//            if (email.isEmpty()) {
//                binding.edtEmail.error = "Email Harus Diisi"
//                binding.edtEmail.requestFocus()
//                return@setOnClickListener
//            }
//
//            //Validasi email tidak sesuai
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                binding.edtEmail.error = "Email Tidak Valid"
//                binding.edtEmail.requestFocus()
//                return@setOnClickListener
//            }
//
//            //Validasi username
//            if(username.isEmpty()){
//                binding.edtUsername.error = "Username Harus Diisi"
//                binding.edtUsername.requestFocus()
//                return@setOnClickListener
//            }
//
//            //Validasi password
//            if (password.isEmpty()) {
//                binding.edtPass.error = "Password Harus Diisi"
//                binding.edtPass.requestFocus()
//                return@setOnClickListener
//            }
//
//            //Validasi panjang password
//            if (password.length < 6) {
//                binding.edtPass.error = "Password Minimal 6 Karakter"
//                binding.edtPass.requestFocus()
//                return@setOnClickListener
//            }
//
//            RegisterFirebase(usernameGithub, email, username, password)
//        }
//    }

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

//    private fun RegisterFirebase(usernameGithub : String, email: String, username : String, password: String) {
//        auth.createUserWithEmailAndPassword(usernameGithub, email, username, password)
//            .addOnCompleteListener(this) {
//                if (it.isSuccessful) {
//                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }


