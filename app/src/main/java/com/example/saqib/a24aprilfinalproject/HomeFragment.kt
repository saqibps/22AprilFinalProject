package com.example.saqib.a24aprilfinalproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import java.util.ArrayList


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_home, container, false)

        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts")
        postList = arrayListOf()

        databaseReference.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}

            override fun onChildRemoved(p0: DataSnapshot?) {}
        })

        return view
    }
}
