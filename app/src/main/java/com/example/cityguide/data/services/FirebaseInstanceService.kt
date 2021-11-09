package com.example.cityguide.data.services

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseInstanceService: FirebaseMessagingService(){

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("NEW TOKEN", p0)
    }
}