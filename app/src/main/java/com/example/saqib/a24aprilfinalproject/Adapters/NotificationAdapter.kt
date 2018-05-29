package com.example.saqib.a24aprilfinalproject.Adapters

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.saqib.a24aprilfinalproject.Notification
import com.example.saqib.a24aprilfinalproject.Post
import com.example.saqib.a24aprilfinalproject.R
import kotlinx.android.synthetic.main.notification_item_layout.view.*

class NotificationAdapter(val notificationList:ArrayList<Notification>)
    : RecyclerView.Adapter<NotificationAdapter.NotificationItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item_layout,parent,false)
        return NotificationItemViewHolder(view)
    }
    override fun getItemCount()= notificationList.size
    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bindView(notificationList[position])
    }
    inner class NotificationItemViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val notificationView:TextView = itemView.findViewById(R.id.notification_tv)

        fun bindView(notification:Notification){
            val name = notification.name
            val desc = notification.desc.removePrefix(name)
            val spannableString = SpannableString(name)
            spannableString.setSpan(StyleSpan(Typeface.BOLD),0,spannableString.length,0)
            notificationView.append(spannableString)
            notificationView.append(desc)

        }

    }
}