package com.example.saqib.a24aprilfinalproject

data class Post(val key:String,
                val uid:String,
                val userName:String,
                val unitsRequired:Int,
                var donationReceived:Int,
                val bloodGroup:String,
                val urgency:String,
                val contact:String,
                val additionalInstruction:String,
                val location:String,
                val hospital:String,
                val relationWithPatient:String,
                val commentsKey: String?,
                val volunteerList: ArrayList<Volunteer>?) {
    constructor():this("","","",0,0,"","","",
            "","","","","",null)
}