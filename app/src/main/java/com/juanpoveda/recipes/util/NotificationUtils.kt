package com.juanpoveda.recipes.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.juanpoveda.recipes.R
import com.juanpoveda.recipes.receiver.RecipesBroadcastReceiver
import com.juanpoveda.recipes.ui.MainActivity

// ****Notifications s4: Add the Notification id. This ID represents the current notification instance and is needed for updating or canceling this
// notification. Since your app will only have one active notification at a given time, you can use the same ID for all your notifications
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0


// ****Notifications s1: Create a NotificationUtils class. Here we'll define the code to show/cancel the notifications using extension functions.
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context, showLargeImage: Boolean, showAction: Boolean) {
    // ****Notifications s14: Add an intent variable. We want to attach the intent to the notification, to launch it when the user taps on the notification.
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    // ****Notifications s15: The notification is displayed outside the app so to make an intent work outside the app, we need to create a new PendingIntent.
    // PendingIntent grants rights to another application or the system to perform an operation on behalf of your application. A PendingIntent
    // is simply a reference to a token maintained by the system describing the original data used to retrieve it. In this case, the system will use the
    // pending intent to open the app on behalf of you, regardless of whether or not the timer app is running.
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // ****Notifications s19: To customize the notification with a big image, create the Bitmap and the BigPictureStyle
    val cookingImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.cooking)
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(cookingImage)
        .bigLargeIcon(null)

    // ****Notifications s21 (Optional): Create a new intent for a broadcastReceiver to be launched when a user taps on the action button inside the notification.
    // A notification can offer up to three action buttons that allow the user to respond quickly, such as snooze a reminder or reply to a text message.
    // These action buttons should not duplicate the action performed when the user taps the notification
    val receiverIntent = Intent(applicationContext, RecipesBroadcastReceiver::class.java)
    val receiverPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        receiverIntent,
        FLAGS)

    // ****Notifications s2: Get an instance of Notification builder. Pass in the app context and a channel ID. The channel ID is a string value for the channel.
    // Notification Channels are a way to group notifications. By grouping together similar types of notifications, developers and users can control all of
    // the notifications in the channel. Once a channel is created, it can be used to deliver any number of notifications.
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.recipe_notification_channel_id)
    ) // ****Notifications s3: In the builder, set the small icon, content title and content text.
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        // ****Notifications s16 (Optional): Pass the pendingIntent to the notificationBuilder. Also set setAutoCancel() to true, so that when the user taps on the
        // notification, the notification dismisses itself as it takes them to the app.
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

    // ****Notifications s20 (Optional): Add the previously created style
    if (showLargeImage) {
        builder.setStyle(bigPicStyle)
            .setLargeIcon(cookingImage)
    }

    // ****Notifications s22 (Optional): Add the action with the created pendingIntent in the notification builder. In this example, when the user clicks in the
    // action button, a new notification will be launched telling the user that the save operation was successful
    if (showAction) {
        builder.addAction(
            R.drawable.ic_launcher_foreground,
            applicationContext.getString(R.string.notification_action_button_text),
            receiverPendingIntent
        )
    }

    // ****Notifications s5: Call notify
    notify(NOTIFICATION_ID, builder.build())
}

// ****Notifications s17: Add a method to dismiss all the notifications in NotificationUtils using extension functions
fun NotificationManager.cancelNotifications() {
    cancelAll()
}