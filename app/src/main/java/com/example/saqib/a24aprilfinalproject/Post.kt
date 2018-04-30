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
                var volunteersUptilNow:Int,
                val location:String,
                val hospital:String,
                val relationWithPatient:String,
                val commentsId: String?,
                val list: List<Volunteer>?) {
    constructor():this("","","",0,0,"","","",
            "",0,"","","","",null)
}