package com.example.saqib.a24aprilfinalproject.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.saqib.a24aprilfinalproject.R
import com.example.saqib.a24aprilfinalproject.User
import com.example.saqib.a24aprilfinalproject.Volunteer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.volunteer_item_layout.*
import org.w3c.dom.Text

class VolunteerAdapter(val volunteerList:ArrayList<Volunteer>,val postBloodGroup:String): RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        return VolunteerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.volunteer_item_layout,parent,false))
    }
    override fun getItemCount()= volunteerList.size
    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {holder.bindView(volunteerList[position])}

    inner class VolunteerViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        val nameTV:TextView = itemView.findViewById(R.id.name_tv_volunteer_item)
        val bloodGroupTV:TextView = itemView.findViewById(R.id.blood_group_tv_volunteer_item)
        val exchangeTV:TextView = itemView.findViewById(R.id.exchange_donation_tv_volunteer_item)
        val donationStatusTV:TextView = itemView.findViewById(R.id.donation_status_tv_volunteer_item)

        fun bindView(volunteer: Volunteer) {
            var fullName:String = ""
            var bloodGroup:String = ""
            val databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(volunteer.uid)
            databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user:User = snapshot.getValue(User::class.java)!!
                    fullName = "${user.firstName} ${user.lastName}"
                    Log.e("my checking","full Name : $fullName")
                    bloodGroup = user.bloodGroup
                    Log.e("my checking","blood group : $bloodGroup")
                }
            })

            nameTV.text = "Hard coded name in adapter"
            bloodGroupTV.text = bloodGroup
            if (bloodGroup.equals(postBloodGroup)) {
                exchangeTV.visibility = View.GONE
            } else {
                exchangeTV.visibility = View.VISIBLE
                exchangeTV.text = "Exchange Donation"
            }
            donationStatusTV.text = volunteer.donationStatus
        }
    }

}