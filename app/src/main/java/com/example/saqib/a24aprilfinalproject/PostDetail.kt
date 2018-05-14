package com.example.saqib.a24aprilfinalproject


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.*
import com.example.saqib.a24aprilfinalproject.Adapters.CommentAdapter
import com.example.saqib.a24aprilfinalproject.Adapters.VolunteerAdapter
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
    lateinit var volunteerAdapter: VolunteerAdapter
    lateinit var commentRecyclerView: RecyclerView
    lateinit var commentAdapter: CommentAdapter
    lateinit var postKey:String
    var postBloodGroup:String = ""
    var commentKey:String = ""
    var volunteerKey:String = ""
    lateinit var auth: FirebaseAuth
    lateinit var uid:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_post_detail, container, false)

        val bundle = this.arguments
        if (bundle != null) {
             postKey = bundle.getString("postKey")
            volunteerKey = bundle.getString("volunteerKey")
            postBloodGroup = bundle.getString("postBloodGroup")
        }
        volunteerList = arrayListOf()
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser!!.uid

        volunteerAdapter = VolunteerAdapter(volunteerList,postBloodGroup)
        volunteerRecyclerView = view.findViewById(R.id.volunteers_recycler_view_post_detail)
        volunteerRecyclerView.layoutManager = LinearLayoutManager(context)
        volunteerRecyclerView.adapter = volunteerAdapter
        volunteerDatabaseReference = FirebaseDatabase.getInstance().reference.child("volunteers").child(volunteerKey)
        Log.e("In Post Detail","Volunteers key : $volunteerKey")

        postDatabaseReference = FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
        postDatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snapshot: DataSnapshot?) {
                val post = snapshot!!.getValue(Post::class.java)
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
                commentKey = post.commentsKey!!
                Log.e("InValueEvent","${post.volunteerKey}")
                Log.e("Now Volunteer key is","$volunteerKey")
            }
        })
        volunteerDatabaseReference.addChildEventListener(object :ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
                val volunteer = snapshot!!.getValue(Volunteer::class.java)
                if (volunteer != null) {
//                        Toast.makeText(context,"volunteer key : $volunteerKey",Toast.LENGTH_LONG).show()
                    if (volunteer.donationStatus.equals("")) {
                        Toast.makeText(context, "Donation status is empty", Toast.LENGTH_SHORT).show()
                    }
                    volunteerList.add(volunteer)
                    volunteerAdapter.notifyDataSetChanged()
                }
            }
        })


//
//        if (commentKey != null) {
//            commentList = arrayListOf()
//            commentAdapter = CommentAdapter(commentList)
//            commentRecyclerView = view.findViewById(R.id.comments_recycler_view_post_detail)
//            commentDatabaseReference = FirebaseDatabase.getInstance().reference.child("comments").child(commentKey)
//            commentDatabaseReference.addChildEventListener(object :ChildEventListener{
//                override fun onCancelled(p0: DatabaseError?) {}
//                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
//                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    val comment = snapshot.getValue(Comment::class.java)
//                    if (comment != null) {
//                        commentList.add(comment)
//                        commentAdapter.notifyDataSetChanged()
//                    }
//                }
//                override fun onChildRemoved(p0: DataSnapshot?) {}
//            })
//        }

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