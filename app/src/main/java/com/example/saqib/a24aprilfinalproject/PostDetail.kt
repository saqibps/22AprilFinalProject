package com.example.saqib.a24aprilfinalproject


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
    lateinit var comment_et:EditText
    lateinit var comment_bt:Button
    lateinit var commentAdapter: CommentAdapter
    lateinit var postKey:String
    lateinit var post: Post
    lateinit var userName:String
    var postBloodGroup:String = ""
    var commentKey:String = ""
    var volunteerKey:String = ""
    lateinit var auth: FirebaseAuth
    lateinit var uid:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_post_detail, container, false)
        val bundle = this.arguments
        if (bundle != null) {
             postKey = bundle.getString("postKey")
            volunteerKey = bundle.getString("volunteerKey")
            postBloodGroup = bundle.getString("postBloodGroup")
            commentKey = bundle.getString("postCommentKey")
        }
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser!!.uid

        val usersDatabaseRef = FirebaseDatabase.getInstance().reference.child("users").child(uid)
        usersDatabaseRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    userName = "${user.firstName} ${user.lastName}"
                }
            }
        })
             comment_et = view.findViewById(R.id.comment_et_post_detail_fragment)
             comment_bt = view.findViewById(R.id.comment_bt_post_detail_fragment)
        comment_et.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                comment_bt.isEnabled = comment_et.text.toString().isNotEmpty()
            }
        })
        val name_tv_post_detail_fragment:TextView = view.findViewById(R.id.name_tv_post_detail_fragment)
        val units_req_tv_post_detail_fragment:TextView = view.findViewById(R.id.units_req_tv_post_detail_fragment)
        val donation_received_tv_post_detail_fragment:TextView = view.findViewById(R.id.donation_received_tv_post_detail_fragment)
        val still_required_tv_post_detail_fragment:TextView = view.findViewById(R.id.still_required_tv_post_detail_fragment)
        val blood_group_tv_post_detail_fragment:TextView = view.findViewById(R.id.blood_group_tv_post_detail_fragment)
        val location_tv_post_detail_fragment:TextView = view.findViewById(R.id.location_tv_post_detail_fragment)
        val hospital_tv_post_detail_fragment:TextView = view.findViewById(R.id.hospital_tv_post_detail_fragment)
        val urgency_tv_post_detail_fragment:TextView = view.findViewById(R.id.urgency_tv_post_detail_fragment)
        val relation_tv_post_detail_fragment:TextView = view.findViewById(R.id.relation_tv_post_detail_fragment)
        val contact_tv_post_detail_fragment:TextView = view.findViewById(R.id.contact_tv_post_detail_fragment)
        val instruction_tv_post_detail_fragment:TextView = view.findViewById(R.id.instruction_tv_post_detail_fragment)



        postDatabaseReference = FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
        postDatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(snapshot: DataSnapshot?) {
                post = snapshot!!.getValue(Post::class.java)!!
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
                // Since this listener callbacks run after child listener of comments database and volunteer database,
                        // SO we are not saving commentkey and volunteerkey value here.
                        // else we are passing these values from previous fragment.
//                volunteerKey = post.volunteerKey!!
//                commentKey = post.commentsKey!!
            }
        })

        volunteerList = arrayListOf()
        volunteerAdapter = VolunteerAdapter(volunteerList,postBloodGroup)
        volunteerRecyclerView = view.findViewById(R.id.volunteers_recycler_view_post_detail)
        volunteerRecyclerView.layoutManager = LinearLayoutManager(context)
        volunteerRecyclerView.adapter = volunteerAdapter
        volunteerDatabaseReference = FirebaseDatabase.getInstance().reference.child("volunteers").child(volunteerKey)
        volunteerDatabaseReference.addChildEventListener(object :ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
                val volunteer = snapshot!!.getValue(Volunteer::class.java)
                if (volunteer != null) {
                    volunteerList.add(volunteer)
                    volunteerAdapter.notifyDataSetChanged()
                }
            }
        })
            commentList = arrayListOf()
            commentAdapter = CommentAdapter(commentList)
            commentRecyclerView = view.findViewById(R.id.comments_recycler_view_post_detail)
            commentRecyclerView.layoutManager = LinearLayoutManager(context)
            commentRecyclerView.adapter = commentAdapter
            commentDatabaseReference = FirebaseDatabase.getInstance().reference.child("comments").child(commentKey)
            commentDatabaseReference.addChildEventListener(object :ChildEventListener{
                override fun onCancelled(p0: DatabaseError?) {}
                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot?) {}
                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    Log.e("In post detail","On child added is called")
                    val comment = snapshot.getValue(Comment::class.java)
                    if (comment != null) {
                        commentList.add(comment)
                        commentAdapter.notifyDataSetChanged()
                    }
                }

            })
        comment_bt.setOnClickListener {
            val commentDesc = comment_et.text.toString()
            if (commentKey.equals("")) {
                commentKey = FirebaseDatabase.getInstance().reference.child("comments").push().key
                FirebaseDatabase.getInstance().reference.child("posts").child(postKey).child("commentsKey").setValue(commentKey)
                post.commentsKey = commentKey
            }
            val  particularCommentKey = FirebaseDatabase.getInstance().reference.child("comments")
                    .child(commentKey).push().key
            val comment = Comment(userName,particularCommentKey,commentDesc)
            Log.e("In Post detail","post comment key: $commentKey, particular comment Key: $particularCommentKey")
            FirebaseDatabase.getInstance().reference.child("comments").child(commentKey).child(particularCommentKey)
                    .setValue(comment)
            comment_et.setText("")
        }
        return view
    }
}