package com.example.teamapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton  // Ubah import menjadi ini
import com.example.teamapp.ui.LoginActivity

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val buttonLogout = view.findViewById<AppCompatButton>(R.id.logoutbutton)

        // Mendapatkan SharedPreferences
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("login_status", AppCompatActivity.MODE_PRIVATE)

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
