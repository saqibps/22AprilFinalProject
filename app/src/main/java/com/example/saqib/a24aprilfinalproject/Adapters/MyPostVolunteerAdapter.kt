package com.example.saqib.a24aprilfinalproject.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.saqib.a24aprilfinalproject.Post
import com.example.saqib.a24aprilfinalproject.R
import com.example.saqib.a24aprilfinalproject.User
import com.example.saqib.a24aprilfinalproject.Volunteer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.my_post_volunteer_item_layout.view.*

class MyPostVolunteerAdapter(val volunteerList:ArrayList<Volunteer>,val postBloodGroup:String,val volunteerKey:String,
                             val postKey:String) : RecyclerView.Adapter<MyPostVolunteerAdapter.VolunteerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        return VolunteerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.my_post_volunteer_item_layout,parent,false))
    }

    override fun getItemCount()= volunteerList.size

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {holder.bindView(volunteerList[position])}

    inner class VolunteerViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        val nameTV :TextView = itemView.findViewById(R.id.name_tv__my_post_detail_volunteer_item)
        val bloodGroupTV :TextView = itemView.findViewById(R.id.blood_group_tv__my_post_detail_volunteer_item)
        val exchangeTV:TextView = itemView.findViewById(R.id.exchange_donation_tv_my_post_detail_volunteer_item)
        val donatedBT:Button = itemView.findViewById(R.id.donated_bt_my_post_detail_volunteer_item)
        val notDonatedBt:Button = itemView.findViewById(R.id.notdonated_bt_my_post_detail_volunteer_item)

        fun bindView(volunteer:Volunteer) {
            var fullName = ""
            var bloodGroup = ""
            val databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(volunteer.uid)
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot?) {
                    val user: User = snapshot!!.getValue(User::class.java)!!
                    fullName = "${user.firstName} ${user.lastName}"
                    bloodGroup = user.bloodGroup
                    nameTV.text = fullName
                    bloodGroupTV.text = bloodGroup
                    if (bloodGroup.equals(postBloodGroup)) {
                        exchangeTV.visibility = View.GONE
                    } else {
                        exchangeTV.visibility = View.VISIBLE
                        exchangeTV.text = "Exchange Donation"
                    }
                }
                override fun onCancelled(p0: DatabaseError?) {}
            })
            if (volunteer.donationStatus.equals(donatedBT.text.toString())) {
                donatedBT.isEnabled = false
                notDonatedBt.isEnabled = true
            } else if(volunteer.donationStatus.equals(notDonatedBt.text.toString())) {
                donatedBT.isEnabled = true
                notDonatedBt.isEnabled = false
            }
            donatedBT.setOnClickListener {
                FirebaseDatabase.getInstance().reference.child("volunteers").child(volunteerKey).child(volunteer.uid)
                        .child("donationStatus").setValue(donatedBT.text.toString())

                FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
                        .addListenerForSingleValueEvent(object:ValueEventListener{
                            override fun onCancelled(p0: DatabaseError?) {}
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val post = snapshot.getValue(Post::class.java)
                                var donationReceived = post!!.donationReceived
                                donationReceived++
                                FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
                                        .child("donationReceived").setValue(donationReceived)
                                donatedBT.isEnabled = false
                                notDonatedBt.isEnabled = true
                            }
                        } )
            }
            notDonatedBt.setOnClickListener{
                FirebaseDatabase.getInstance().reference.child("volunteers").child(volunteerKey).child(volunteer.uid)
                        .child("donationStatus").setValue(notDonatedBt.text.toString())

                FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
                        .addListenerForSingleValueEvent(object:ValueEventListener{
                            override fun onCancelled(p0: DatabaseError?) {}
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val post = snapshot.getValue(Post::class.java)
                                var donationReceived = post!!.donationReceived
                                donationReceived--
                                FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
                                        .child("donationReceived").setValue(donationReceived)
                                notDonatedBt.isEnabled = false
                                donatedBT.isEnabled = true
                            }
                        } )

            }


        }
    }
}