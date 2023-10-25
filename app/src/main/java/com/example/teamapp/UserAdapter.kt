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

class UserAdapter(private val users: MutableList<DataItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val filteredUsers: MutableList<DataItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_item, parent, false)
        return ListViewHolder(view)
    }

    fun addUser(newUsers: DataItem) {
        users.add(newUsers)
        notifyDataSetChanged()
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (filteredUsers.isNotEmpty()) filteredUsers.size else users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = if (filteredUsers.isNotEmpty()) filteredUsers[position] else users[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon_launcher))
            .transform(CircleCrop())
            .into(holder.ivAvatar)

        holder.tvUserName.text = "${user.firstName} ${user.lastName}"
        holder.tvEmail.text = user.email
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName: TextView = itemView.findViewById(R.id.itemContent)
        var tvEmail: TextView = itemView.findViewById(R.id.itemNumber)
        var ivAvatar: ImageView = itemView.findViewById(R.id.itemImage)
    }

    fun filterUsers(query: String) {
        filteredUsers.clear()
        for (user in users) {
            if (user.firstName?.toLowerCase()?.contains(query) == true ||
                user.lastName?.toLowerCase()?.contains(query) == true ||
                user.email?.toLowerCase()?.contains(query) == true
            ) {
                filteredUsers.add(user)
            }
        }
        notifyDataSetChanged()
    }

    fun resetFilter() {
        filteredUsers.clear()
        notifyDataSetChanged()
    }
}
