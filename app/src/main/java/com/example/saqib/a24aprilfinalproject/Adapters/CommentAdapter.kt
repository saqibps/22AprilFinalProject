package com.example.saqib.a24aprilfinalproject.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.saqib.a24aprilfinalproject.Comment
import com.example.saqib.a24aprilfinalproject.R


class CommentAdapter(val commentList:ArrayList<Comment>): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_item_layout,parent, false)
        return CommentViewHolder(itemView)
    }

    override fun getItemCount()= commentList.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindView(commentList[position])
    }

    inner class CommentViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        val nameTV:TextView = itemView.findViewById(R.id.name_tv_comment_item)
        val descTV:TextView = itemView.findViewById(R.id.desc_tv_comment_item)

        fun bindView(comment:Comment){
            nameTV.text = comment.userName
            descTV.text = comment.desc
        }

    }
}