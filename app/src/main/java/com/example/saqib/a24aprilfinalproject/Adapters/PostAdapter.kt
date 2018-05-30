package com.example.saqib.a24aprilfinalproject.Adapters

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.saqib.a24aprilfinalproject.Post
import com.example.saqib.a24aprilfinalproject.PostDetail
import com.example.saqib.a24aprilfinalproject.R
import com.example.saqib.a24aprilfinalproject.Volunteer
import com.google.firebase.database.*

class PostAdapter(val postList: ArrayList<Post>,val uid:String,val activity: FragmentActivity,
                  val listener:(Post) -> Unit) : RecyclerView.Adapter<PostAdapter.PostItemVIewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemVIewHolder {
        val itemView:View = LayoutInflater.from(parent.context).inflate(R.layout.post_item_layout,parent,false)
        return PostItemVIewHolder(itemView)
    }
    override fun getItemCount()= postList.size
    override fun onBindViewHolder(holder: PostItemVIewHolder, position: Int) {
        holder.bindView(postList[position])
    }

    inner class PostItemVIewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.name_tv_post_item)
        val descTV: TextView = itemView.findViewById(R.id.desc_tv_post_item)
        val urgencyTV: TextView = itemView.findViewById(R.id.urgency_tv_post_item)
        val contactTV: TextView = itemView.findViewById(R.id.contact_tv_post_item)
        val instructionTV: TextView = itemView.findViewById(R.id.instructions_tv_post_item)
        val volunteerTV: TextView = itemView.findViewById(R.id.volunteers_tv_post_item)
        val requirementTV: TextView = itemView.findViewById(R.id.current_requirement_tv_post_item)
        val volunteerBT: Button = itemView.findViewById(R.id.volunteer_bt_post_item)
        val commentBT: Button = itemView.findViewById(R.id.comment_bt_post_item)

        fun bindView(post: Post) {
            nameTV.text = post.userName
            descTV.text = "${post.unitsRequired} units of ${post.bloodGroup} blood Required.\nAt ${post.hospital} for my ${post.relationWithPatient}."
            urgencyTV.text = post.urgency
            contactTV.text = post.contact
            volunteerTV.text = post.volunteerUptilNow.toString()
            instructionTV.text = post.additionalInstruction
            requirementTV.text = (post.unitsRequired - post.donationReceived).toString()
            var isVolunteer:Boolean
            FirebaseDatabase.getInstance().reference.child("volunteers").child(post.volunteerKey)
                    .addChildEventListener(object :ChildEventListener{
                        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
                        override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
                        override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
                            val volunteer = snapshot!!.getValue(Volunteer::class.java)
                            if (volunteer != null) {
                                if (volunteer.uid == uid) {
                                    volunteerBT.text = "Unvolunteer"
                                }
                            }
                        }
                        override fun onChildRemoved(p0: DataSnapshot?) {}
                        override fun onCancelled(p0: DatabaseError?) {}
                    })

            volunteerBT.setOnClickListener {
                var volunteerKey = post.volunteerKey
                if (volunteerBT.text.equals("Volunteer")) {
                    if (volunteerKey.equals("")) {
                        volunteerKey = FirebaseDatabase.getInstance().reference.child("volunteers").push().key
                        FirebaseDatabase.getInstance().reference.child("posts").child(post.key).child("volunteerKey").setValue(volunteerKey)
                        post.volunteerKey = volunteerKey
                    }
                    volunteerBT.text = "Unvolunteer"
                    val volunteer = Volunteer(uid, "Not Donated")
                    FirebaseDatabase.getInstance().reference.child("volunteers").child(volunteerKey).child(uid).setValue(volunteer)
                    var volunteers = post.volunteerUptilNow
                    volunteers++
                    post.volunteerUptilNow = volunteers
                    FirebaseDatabase.getInstance().reference.child("posts").child(post.key).child("volunteerUptilNow").setValue(volunteers)
                    notifyDataSetChanged()
                } else {
                    volunteerBT.text = "Volunteer"
                    FirebaseDatabase.getInstance().reference.child("volunteers").child(volunteerKey).child(uid).removeValue()
                    var volunteers = post.volunteerUptilNow
                    volunteers--
                    post.volunteerUptilNow = volunteers
                    FirebaseDatabase.getInstance().reference.child("posts").child(post.key).child("volunteerUptilNow").setValue(volunteers)
                    notifyDataSetChanged()
                }
            }
                commentBT.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("postKey",post.key)
                    bundle.putString("volunteerKey",post.volunteerKey)
                    bundle.putString("postBloodGroup",post.bloodGroup)
                    bundle.putString("postCommentKey",post.commentsKey)
                    val postDetail = PostDetail()
                    postDetail.arguments = bundle
                    activity.supportFragmentManager.beginTransaction().replace(R.id.frame_layout,postDetail)
                            .addToBackStack(null).commit()
                }
                itemView.setOnClickListener {
                    listener(post)
                }
            }
        }
}