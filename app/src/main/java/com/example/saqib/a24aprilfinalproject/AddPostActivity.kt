package com.example.saqib.a24aprilfinalproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_post.*

class AddPostActivity : AppCompatActivity() {

    lateinit var postdatabaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var uid:String
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        auth = FirebaseAuth.getInstance()
            uid = auth.currentUser!!.uid
        val database = FirebaseDatabase.getInstance().reference.child("users").child(uid)
        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
            }
        })
        postdatabaseReference = FirebaseDatabase.getInstance().reference.child("posts")

        add_bt_add_post.setOnClickListener {

            if (no_of_units_required_add_new_et.text.isEmpty() ||
                    contact_add_new_et.text.isEmpty() ||
                    additional_instruction_add_new_et.text.isEmpty()) {
                Toast.makeText(this,"Please Fill All Fields",Toast.LENGTH_SHORT).show()
            } else {
                val bloodGroup: String = blood_group_spinner_add_post.selectedItem.toString()
                val unitsReq:Int = no_of_units_required_add_new_et.text.toString().trim().toInt()
                val urgency: String = urgency_spinner_add_post.selectedItem.toString()
                val country: String = country_spinner_add_post.selectedItem.toString()
                val state: String = state_spinner_add_post.selectedItem.toString()
                val city: String = city_spinner_add_post.selectedItem.toString()
                val hospital: String = hospital_spinner_add_post.selectedItem.toString()
                val relation: String = relation_spinner_add_post.selectedItem.toString()
                val contact: String = contact_add_new_et.text.toString().trim()
                val additionInstruction: String = additional_instruction_add_new_et.text.toString()
                val userName = "${user.firstName} ${user.lastName}"
                val location = "$city,$state,$country"

                val key:String = postdatabaseReference.push().key
                val post = Post(key,uid,userName,unitsReq,0,0,"not fulfilled",bloodGroup,urgency,contact,additionInstruction,
                        location,hospital,relation,null,null)
                postdatabaseReference.child(key).setValue(post)
                val notificationDesc = "$userName required $unitsReq units of $bloodGroup at $hospital."
                val notification = Notification(key,userName,notificationDesc)
                FirebaseDatabase.getInstance().reference.child("notifications").child(key).setValue(notification)
                finish()
            }

        }
    }
}
