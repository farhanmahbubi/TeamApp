package com.example.teamapp.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamapp.utils.Result
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamapp.databinding.FragmentHomeBinding
import com.example.teamapp.detail.Detail
import com.example.teamapp.model.ResponseUserGithub


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy {
        UserAdapter { user ->
            val intent = Intent(requireContext(), Detail::class.java).apply {
                putExtra("username", user.login)
            }
            startActivity(intent)
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.adapter = adapter

        viewModel.resultUser.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ResponseUserGithub.Item>)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()

        return binding.root
    }
}
