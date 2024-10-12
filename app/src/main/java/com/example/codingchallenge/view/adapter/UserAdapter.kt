package com.example.codingchallenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.LayoutItemUserBinding
import com.example.codingchallenge.model.data.User
import com.example.codingchallenge.model.interfaces.OnUserItemClickListener

class UserAdapter(
    private var users: List<User>,
    private val itemClickListener: OnUserItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            LayoutItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, itemClickListener)
    }

    fun updateList(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size

    class UserViewHolder(private val binding: LayoutItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, itemClickListener: OnUserItemClickListener) {
            binding.user = user
            binding.itemClickListener = itemClickListener
            // Load image using Glide
            Glide.with(binding.root.context)
                .load(user.picture.large)
                .placeholder(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(binding.userImage)
            binding.executePendingBindings()
        }
    }
}
