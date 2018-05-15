package com.example.saqib.a24aprilfinalproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.saqib.a24aprilfinalproject.Adapters.PostAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.HashMap


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    lateinit var databaseReference: DatabaseReference
    lateinit var postList:ArrayList<Post>
    lateinit var postAdapter:PostAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_home_fragment)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child("posts")
        postList = arrayListOf()
        postAdapter = PostAdapter(postList,uid, activity!!) { post ->
            val bundle = Bundle()
            bundle.putString("postKey",post.key)
            bundle.putString("volunteerKey",post.volunteerKey)
            bundle.putString("postBloodGroup",post.bloodGroup)
            bundle.putString("postCommentKey",post.commentsKey)
            val postDetail = PostDetail()
            postDetail.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame_layout,postDetail)
                    .addToBackStack(null).commit()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter


        databaseReference.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                if (snapshot != null) {
                    val post = snapshot.getValue(Post::class.java)
                    if (post != null) {
                        postList.add(post)
                        postAdapter.notifyDataSetChanged()
                    }

                }
            }
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
        })

        return view
    }
}
