package com.juanpoveda.recipes.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.util.cancelNotifications
import com.juanpoveda.recipes.util.sendNotification

class RecipesBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelNotifications()
        notificationManager.sendNotification(context.getString(R.string.notification_action_clicked_message),
            context, showLargeImage = false, showAction = false)
    }
}