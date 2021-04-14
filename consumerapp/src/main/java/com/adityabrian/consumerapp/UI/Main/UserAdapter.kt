package com.adityabrian.consumerapp.UI.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityabrian.consumerapp.Model.User
import com.adityabrian.consumerapp.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    /** jadi setList akan dipanggil di ViewModel kemudian listnya dibersihkan dulu dengan list.clear()
     * lalu list di isi ulang dengan data baru yaitu list.addAll(users),
     * lalu notifyDataSetChange memberi tahu kalau data setnya berubah*/
    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                /** kita set ada nama dan gambar */
                Glide.with(itemView)
                    .load(user.avatar_url)
                    /** DrawableTransitionOptions.withCrossFade()
                     *  SECARA langsung nant gambarnya tidak langsung menjiplak muncul tiba tiba
                     *  tetapi muncul dengan smooth*/
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(IVUser)

                TVUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}