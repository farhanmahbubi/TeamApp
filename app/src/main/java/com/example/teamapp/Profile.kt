package com.example.teamapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton  // Ubah import menjadi ini
import com.example.teamapp.ui.LoginActivity

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val buttonLogout = view.findViewById<AppCompatButton>(R.id.logoutbutton)  // Ubah sesuai dengan ID yang ada di XML

        buttonLogout.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish() // Menutup aktivitas saat logout
        }

        return view
    }
}
