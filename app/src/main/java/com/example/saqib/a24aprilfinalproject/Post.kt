package com.example.saqib.a24aprilfinalproject

data class Post(val key:String,
                val uid:String,
                val userName:String,
                var unitsRequired:Int,
                var donationReceived:Int,
                var volunteerUptilNow:Int,
                val status:String,
                val bloodGroup:String,
                val urgency:String,
                val contact:String,
                val additionalInstruction:String,
                val location:String,
                val hospital:String,
                val relationWithPatient:String,
                var commentsKey: String?,
                var volunteerKey: String?) {
    constructor():this("","","",0,0,0,"","","","",
            "","","","","","")
}