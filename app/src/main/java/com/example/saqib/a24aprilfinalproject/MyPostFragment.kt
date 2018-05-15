package com.example.saqib.a24aprilfinalproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saqib.a24aprilfinalproject.Adapters.MyPostAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MyPostFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var uid:String
    lateinit var postList: ArrayList<Post>
    lateinit var recyclerView:RecyclerView
    lateinit var myPostAdapter:MyPostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_my_post, container, false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser!!.uid

        recyclerView = view.findViewById(R.id.recycler_view_my_post_fragment)
        recyclerView.layoutManager = LinearLayoutManager(context)
        postList = arrayListOf()
        myPostAdapter = MyPostAdapter(postList) {post ->
            val bundle = Bundle()
            bundle.putString("postKey",post.key)
            bundle.putString("volunteerKey",post.volunteerKey)
            bundle.putString("postBloodGroup",post.bloodGroup)
            bundle.putString("postCommentKey",post.commentsKey)
            val myPostDetailFragment = MyPostDetailFragment()
            myPostDetailFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame_layout,myPostDetailFragment)
                    .addToBackStack(null).commit()
        }
        recyclerView.adapter = myPostAdapter
        val databaseRef = FirebaseDatabase.getInstance().reference.child("posts")
        databaseRef.addChildEventListener(object :ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                val post = snapshot.getValue(Post::class.java)
                if (post != null) {
                    if (post.uid == uid)
                        postList.add(post)
                    myPostAdapter.notifyDataSetChanged()

                }
            }
        })

        return view
    }


}
