package com.example.saqib.a24aprilfinalproject


import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saqib.a24aprilfinalproject.Adapters.NotificationAdapter
import com.example.saqib.a24aprilfinalproject.Adapters.PostAdapter
import com.google.firebase.auth.FirebaseAuth
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
class NotificationsFragment : Fragment() {

    lateinit var databaseReference: DatabaseReference
    lateinit var notificationList: ArrayList<Notification>
    lateinit var notificationAdapter: NotificationAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_notification_fragment)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child("notifications")
        notificationList = arrayListOf()
        notificationAdapter = NotificationAdapter(notificationList)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(48))
        recyclerView.adapter = notificationAdapter


        databaseReference.orderByChild("timeStamp").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                if (snapshot != null) {
                    val notification = snapshot.getValue(Notification::class.java)
                    if (notification != null) {
                        notificationList.add(notification)
                        notificationAdapter.notifyDataSetChanged()
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

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                    state: RecyclerView.State) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}
