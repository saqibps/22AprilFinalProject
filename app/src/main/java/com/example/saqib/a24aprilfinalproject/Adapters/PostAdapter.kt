package com.example.saqib.a24aprilfinalproject.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saqib.a24aprilfinalproject.Post
import com.example.saqib.a24aprilfinalproject.R

class PostAdapter(val postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostItemVIewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemVIewHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.post_item_layout,parent,false)
        return PostItemVIewHolder(itemView)
    }

    override fun getItemCount()= postList.size


    override fun onBindViewHolder(holder: PostItemVIewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class PostItemVIewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

    }
}