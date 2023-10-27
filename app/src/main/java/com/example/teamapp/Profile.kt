package com.example.teamapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton  // Ubah import menjadi ini
import androidx.appcompat.widget.AppCompatTextView
import com.example.teamapp.database.AppDatabase
import com.example.teamapp.ui.LoginActivity

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val buttonLogout = view.findViewById<AppCompatButton>(R.id.logoutbutton)
        val usernameTextView = view.findViewById<AppCompatTextView>(R.id.textView) // Ganti dengan ID yang sesuai
        val emailTextView = view.findViewById<AppCompatTextView>(R.id.textView2) // Ganti dengan ID yang sesuai

        // Mendapatkan SharedPreferences
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            // Mengambil data user dari Room Database
            val userId = sharedPreferences.getInt("user_id", -1)
            val userDao = AppDatabase.getDatabase(requireContext()).UserDao() // Perhatikan perubahan di sini
            val user = userDao.getUserById(userId)

            // Menampilkan username dan email
            usernameTextView.text = user.appUsername
            emailTextView.text = user.email
        }

        buttonLogout.setOnClickListener {
            // Menghapus status login dari SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            // Navigasi kembali ke LoginActivity
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish() // Menutup aktivitas saat logout
        }

        return view
    }
}
