package com.example.saqib.a24aprilfinalproject.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.saqib.a24aprilfinalproject.Post
import com.example.saqib.a24aprilfinalproject.R

class MyPostAdapter(val postList:ArrayList<Post>): RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_post_item_layout, parent,false)
        return MyPostViewHolder(itemView)
    }
    override fun getItemCount() = postList.size
    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.bindView(postList[position])
    }

    inner class MyPostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val descTV:TextView = itemView.findViewById(R.id.desc_tv_my_post_item_fragment)
        val statusTV:TextView = itemView.findViewById(R.id.status_tv_my_post_item_fragment)

        fun bindView(post: Post) {
            val desc = "Required ${post.unitsRequired} units of ${post.bloodGroup} at ${post.hospital}"
            descTV.text = desc
            statusTV.text = "Hard coded in MyPostAdapter"
        }

    }
}