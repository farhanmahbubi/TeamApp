package com.example.teamapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.parsingjson.model.DataItem
import com.example.teamapp.databinding.FragmentHomeItemBinding
import com.example.teamapp.model.ResponseUserGithub


class UserAdapter(
    private val data: MutableList<ResponseUserGithub.Item> = mutableListOf()
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

            // Menambahkan tindakan klik pada item card
//            itemView.setOnClickListener {
//                itemClickListener(item)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}


//class UserAdapter(private val users: MutableList<DataItem>) :
//    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
//
//    private val filteredUsers: MutableList<DataItem> = mutableListOf()
//    private var isFilterEmpty: Boolean = false
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_item, parent, false)
//        return ListViewHolder(view)
//    }
//
//    fun addUser(newUsers: DataItem) {
//        users.add(newUsers)
//        notifyDataSetChanged()
//    }
//
//    fun clear() {
//        users.clear()
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount(): Int = if (filteredUsers.isNotEmpty()) filteredUsers.size else users.size
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        if (isFilterEmpty) {
//            // Tampilkan pesan jika hasil filter kosong
//            holder.tvUserName.text = ""
//            holder.tvEmail.text = ""
//            holder.ivAvatar.setImageResource(R.color.white)
//        } else {
//            // Tampilkan data user jika hasil filter tidak kosong
//            val user = if (filteredUsers.isNotEmpty()) filteredUsers[position] else users[position]
//
//            Glide.with(holder.itemView.context)
//                .load(user.avatar)
//                .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon_launcher))
//                .transform(CircleCrop())
//                .into(holder.ivAvatar)
//
//            holder.tvUserName.text = "${user.firstName} ${user.lastName}"
//            holder.tvEmail.text = user.email
//        }
//    }
//
//    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var tvUserName: TextView = itemView.findViewById(R.id.itemContent)
//        var tvEmail: TextView = itemView.findViewById(R.id.itemNumber)
//        var ivAvatar: ImageView = itemView.findViewById(R.id.itemImage)
//    }
//
//    fun filterUsers(query: String) {
//        filteredUsers.clear()
//        for (user in users) {
//            if (user.firstName?.toLowerCase()?.contains(query) == true ||
//                user.lastName?.toLowerCase()?.contains(query) == true ||
//                user.email?.toLowerCase()?.contains(query) == true
//            ) {
//                filteredUsers.add(user)
//            }
//        }
//        isFilterEmpty = filteredUsers.isEmpty()
//
//        notifyDataSetChanged()
//    }
//
//    fun resetFilter() {
//        filteredUsers.clear()
//        notifyDataSetChanged()
//    }
//}
