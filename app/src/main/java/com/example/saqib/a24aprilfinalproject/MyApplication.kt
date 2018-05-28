package com.example.saqib.a24aprilfinalproject

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().subscribeToTopic("push_notifications")
    }
}