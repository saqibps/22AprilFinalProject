package com.example.saqib.a24aprilfinalproject

data class Notification(val key:String,val name:String,val desc:String,val timeStamp:Long) {
    constructor():this("","","",0)
}