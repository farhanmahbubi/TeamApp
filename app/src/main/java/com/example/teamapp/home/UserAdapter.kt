package com.example.teamapp.home

import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamapp.databinding.FragmentHomeItemBinding
import com.example.teamapp.model.ResponseUserGithub


class UserAdapter(
    private val data : MutableList<ResponseUserGithub.Item> = mutableListOf(),
    private val listener : (ResponseUserGithub.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ResponseUserGithub.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: FragmentHomeItemBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: ResponseUserGithub.Item) {
            // Menggunakan Glide untuk mengisi gambar pengguna
            Glide.with(v.itemImage)
                .load(item.avatar_url)
                .circleCrop()
                .into(v.itemImage)

            v.itemContent.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}
