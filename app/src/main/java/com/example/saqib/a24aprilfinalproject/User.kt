package com.example.saqib.a24aprilfinalproject

data class User(val uid:String, val firstName:String,val lastName:String,val email:String,val bloodGroup:String) {
    constructor():this("","","","","")
}