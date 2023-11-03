package com.example.teamapp.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.teamapp.R
import com.example.teamapp.database.DbModule
import com.example.teamapp.databinding.FragmentHomeDetailBinding
import com.example.teamapp.model.ResponseDetailUser
import com.example.teamapp.model.ResponseUserGithub
import com.example.teamapp.utils.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Detaill : Fragment() {
    private lateinit var binding: FragmentHomeDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DbModule(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        val username = requireArguments().getString("username") ?: ""
        val item = arguments?.getParcelable<ResponseUserGithub.Item>("item")
        viewModel.resultDetaiUser.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponseDetailUser
                    binding.image.load(user.avatar_url) {
                        transformations(CircleCropTransformation())
                    }
                    binding.nama.text = user.name
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.exception.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)

        viewModel.resultSuksesFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) {
                binding.btnFavorite.changeIconColor(R.color.red)
            }
        }

        viewModel.resultDeleteFavorite.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                binding.btnFavorite.changeIconColor(R.color.white)
            }
        }

        binding.btnFavorite.setOnClickListener {
            item?.let {
                viewModel.setFavorite(it)
            }
        }

        item?.id?.let { itemId ->
            viewModel.findFavorite(itemId) { isFavorite ->
                if (isFavorite as Boolean) {
                    binding.btnFavorite.changeIconColor(R.color.red)
                } else {
                    binding.btnFavorite.changeIconColor(R.color.white)
                }
            }
        }
        return binding.root
    }

    private fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
        Log.d("ChangeIconColor", "Changing icon color to $color")
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }
}
