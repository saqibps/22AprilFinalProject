package com.example.saqib.a24aprilfinalproject

data class Post(val userName:String,
                val unitsRequired:String,
                var donationReceived:Int,
                val bloodGroup:String,
                val urgency:String,
                val contact:String,
                val additionalInstruction:String,
                var VolunteersUptilNow:Int,
                val location:String,
                val hospital:String,
                val relationWithPatient:String,
                val contactNumber:String,
                val commentsId:String,
                val list: List<Volunteer>) {
}