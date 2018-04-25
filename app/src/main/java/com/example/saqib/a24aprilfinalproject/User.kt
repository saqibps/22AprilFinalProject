package com.example.saqib.a24aprilfinalproject

data class User(val firstName:String,val lastName:String,val email:String,val blooddGroup:String) {
    constructor():this("","","","")
}