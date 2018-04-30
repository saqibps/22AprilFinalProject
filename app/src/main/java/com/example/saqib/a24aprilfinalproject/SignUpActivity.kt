package com.example.saqib.a24aprilfinalproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.app.ProgressDialog
import android.content.Intent
import android.util.Log

class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference:DatabaseReference
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Please Wait")

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
        sign_up_bt_sign_up_activity.setOnClickListener {
            val firtNameString:String = first_name_et.text.toString().trim()
            val lastNameString:String = last_name_et.text.toString().trim()
            val passwordString:String = password_et_sign_up.text.toString().trim()
            val emailString:String = email_et_sign_up.text.toString().trim()
            val bloodGroupString:String = spinner_sign_up.selectedItem.toString()

            signUp(firtNameString,lastNameString,emailString,passwordString,bloodGroupString)
            Log.v(this@SignUpActivity.toString(),"$bloodGroupString")
        }

    }

    fun signUp(firstName:String,lastName:String,email:String,password:String,bloodGroup:String){
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful){
                        Toast.makeText(this, "Signed Up!", Toast.LENGTH_SHORT).show()
                        val uid:String = auth.currentUser!!.uid
                        val user:User = User(uid,firstName,lastName,email,bloodGroup)
                        databaseReference.child(uid).setValue(user)

                        gotoApp()
                    } else {
                        Toast.makeText(this, "Error while Signing Up: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

    }
    fun gotoApp() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
