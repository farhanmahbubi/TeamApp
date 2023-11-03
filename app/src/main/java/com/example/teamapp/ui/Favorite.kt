package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamapp.UserAdapter
import com.example.teamapp.database.DbModule
import com.example.teamapp.databinding.FragmentFavoriteBinding
import com.example.teamapp.detail.DetailActivity
import com.example.teamapp.detail.Detaill
import com.example.teamapp.home.UserAdapter

class Favorite : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val adapter by lazy {
        UserAdapter { user ->
            context?.let {
                Intent(it, Detaill::class.java).apply {
                    putExtra("item", user)
                    it.startActivity(this)
                }
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DbModule(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = adapter

        viewModel.getUserFavorite().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
