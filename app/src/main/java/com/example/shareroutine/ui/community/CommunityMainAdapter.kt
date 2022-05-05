package com.example.shareroutine.ui.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.databinding.CommunityCardViewBinding

class CommunityMainAdapter(private var postList: List<Post>) : RecyclerView.Adapter<CommunityMainAdapter.CommunityMainViewHolder>() {
    class CommunityMainViewHolder(val binding: CommunityCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityMainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_card_view, parent, false)

        return CommunityMainViewHolder(CommunityCardViewBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CommunityMainViewHolder, position: Int) {
        val post = postList[position]

        holder.binding.communityCardViewTitle.text = post.title
        holder.binding.communityCardUsername.text = post.username
    }

    override fun getItemCount(): Int = postList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Post>) {
        postList = newList
        notifyDataSetChanged()
    }
}