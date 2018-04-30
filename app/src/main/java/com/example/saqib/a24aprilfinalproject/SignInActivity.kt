package com.example.saqib.a24aprilfinalproject

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            gotoApp()
            return
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Please Wait")


        signup_bt.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))

        }

        signin_bt.setOnClickListener {

            val emailString = email_et.text.toString().trim()
            val passwordString = password_et.text.toString().trim()
            if (emailString.isEmpty() || passwordString.isEmpty()) {
                Toast.makeText(this, "Please provide Email and Password", Toast.LENGTH_SHORT).show()

            } else
                signIn(emailString,passwordString)
        }
    }

    fun gotoApp(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }


    fun signIn(email:String,password:String){
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show()
                        gotoApp()
                    } else{
                        Toast.makeText(this, "Error While Signing Up : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}
