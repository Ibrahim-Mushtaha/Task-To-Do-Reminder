package com.example.todolist.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.todolist.ui.Activity.MainActivity

class AlertReceiver :  BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = MyNotificationManager(context!!)
       val massage= intent!!.getStringExtra("message")
        notificationManager.showNotification(1,"To-Do List Reminder",massage!!,
            Intent(context, MainActivity::class.java)
        )
    }
}