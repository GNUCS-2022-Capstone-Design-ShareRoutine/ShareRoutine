package com.example.shareroutine.ui.community

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.databinding.CommunityCardViewBinding

class CommunityMainAdapter(private var routineList: List<Routine>) : RecyclerView.Adapter<CommunityMainAdapter.ViewHolder>() {
    class ViewHolder(val binding: CommunityCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_card_view, parent, false)

        return ViewHolder(CommunityCardViewBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = routineList[position]

        holder.binding.communityCardViewTitle.text = routine.title
        holder.binding.communityCardUsername.text = routine.username

        holder.binding.communityCardView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("title", routine.title)
            intent.putExtra("username", routine.username)

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = routineList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Routine>) {
        routineList = newList
        notifyDataSetChanged()
    }
}