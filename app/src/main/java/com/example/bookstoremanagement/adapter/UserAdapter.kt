package com.example.bookstoremanagement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.databinding.ItemUserBinding
import com.example.bookstoremanagement.model.UserModel

class UserAdapter(private val userList: List<UserModel>, private val onDeleteClick: (UserModel) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel) {
            binding.tvUserName.text = user.firstName + " " + user.lastName
            binding.tvUserEmail.text = user.email
            binding.btnDeleteUser.setOnClickListener {
                onDeleteClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}
