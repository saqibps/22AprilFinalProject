package com.example.saqib.a24aprilfinalproject

import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var auth: FirebaseAuth
    lateinit var nameTv:TextView
    lateinit var bloodGroupTv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val nav_header:LinearLayout = LayoutInflater.from(this).inflate(R.layout.nav_header_main,nav_view,false) as LinearLayout
        nav_view.addHeaderView(nav_header)
        nameTv = nav_header.findViewById(R.id.nav_header_name_tv)
        bloodGroupTv = nav_header.findViewById(R.id.nav_header_bloodgroup_tv)

        auth = FirebaseAuth.getInstance()
        val uid:String = auth.currentUser!!.uid
        val databaseRef:DatabaseReference = FirebaseDatabase.getInstance().reference.child("users").child(uid)
        databaseRef.addValueEventListener(object :ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(snapshot: DataSnapshot) {
            val user:User = snapshot.getValue(User::class.java)!!
                val fullName = "${user.firstName} ${user.lastName}"
                val bloodGroup = user.bloodGroup
                nameTv.text = fullName
                bloodGroupTv.text = bloodGroup
            }
        })

        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,HomeFragment(),"home_fragment")
                .commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val fragment:Fragment = supportFragmentManager.findFragmentByTag("home_fragment")
            if (fragment is HomeFragment) {
                super.onBackPressed()
                finish()
//
//                Log.v(this.toString(),"backstackentrycount: ${supportFragmentManager.backStackEntryCount}")
//                supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this,AddPostActivity::class.java))
                true
            }
            R.id.action_sign_out -> {
                auth.signOut()
                startActivity(Intent(this,SignInActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout,HomeFragment())
                        .commit()
            }
            R.id.my_request -> {

            }
            R.id.post_requirements -> {

            }
            R.id.notifications -> {

            }
            R.id.action_settings -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}