package com.example.teamapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.parsingjson.model.DataItem
import com.dicoding.parsingjson.model.ResponseUser
import com.dicoding.parsingjson.network.ApiConfig
import com.dicoding.parsingjson.network.ApiService
import com.example.teamapp.databinding.FragmentHomeBinding
import com.example.teamapp.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy {
        UserAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.adapter = adapter

        // Menggunakan CoroutineScope untuk mengambil data dari API
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Memanggil API menggunakan Retrofit di coroutine IO
                val response = withContext(Dispatchers.IO) {
                    ApiClient.githubService.getUserGithub()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Menyimpan data yang diterima dari API ke adapter
                        adapter.setData(responseBody.items)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Data pengguna GitHub tidak ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengambil data pengguna GitHub",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Handle kesalahan jika terjadi
                Toast.makeText(
                    requireContext(),
                    "Terjadi kesalahan: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
