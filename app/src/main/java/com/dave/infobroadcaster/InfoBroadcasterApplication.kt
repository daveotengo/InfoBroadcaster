package com.dave.infobroadcaster

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InfoBroadcasterApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"Coming_inside_onCreate")
    }

    companion object{
        const val Tag = "BroadcasterApplication"
    }

}