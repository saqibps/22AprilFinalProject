package com.example.saqib.a24aprilfinalproject


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.fragment_post_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PostDetail : Fragment() {

    lateinit var postDatabaseReference: DatabaseReference
    lateinit var commentDatabaseReference:DatabaseReference
    lateinit var volunteerDatabaseReference:DatabaseReference
    lateinit var volunteerList:ArrayList<Volunteer>
    lateinit var commentList:ArrayList<Comment>
    lateinit var volunteerRecyclerView:RecyclerView
    lateinit var commentRecyclerView: RecyclerView
    lateinit var postKey:String
    var commentKey:String? = null
    var volunteerKey: String? = null
    lateinit var auth: FirebaseAuth
    lateinit var userName:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_post_detail, container, false)

        val bundle = this.arguments
        if (bundle != null) {
             postKey = bundle.getString("key")
        }
        auth = FirebaseAuth.getInstance()
        postDatabaseReference = FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
        postDatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)
                name_tv_post_detail_fragment.text = post!!.userName
                units_req_tv_post_detail_fragment.text = post.unitsRequired.toString()
                donation_received_tv_post_detail_fragment.text = post.donationReceived.toString()
                still_required_tv_post_detail_fragment.text = (post.unitsRequired - post.donationReceived).toString()
                blood_group_tv_post_detail_fragment.text = post.bloodGroup
                location_tv_post_detail_fragment.text = post.location
                hospital_tv_post_detail_fragment.text = post.hospital
                urgency_tv_post_detail_fragment.text = post.urgency
                relation_tv_post_detail_fragment.text = post.relationWithPatient
                contact_tv_post_detail_fragment.text = post.contact
                instruction_tv_post_detail_fragment.text = post.additionalInstruction
                volunteerKey = post.volunteersKey
                commentKey = post.commentsKey

            }
        })

        return view
    }

    override fun onAttach(context: Context?) {
        Log.v(this.toString(),"On Attached is called")
        super.onAttach(context)
    }

    override fun onDestroy() {
        Log.v(this.toString(),"On Destroy is called")
        super.onDestroy()
    }
}