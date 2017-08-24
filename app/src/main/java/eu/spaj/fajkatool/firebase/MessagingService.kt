package eu.spaj.fajkatool.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import eu.spaj.fajkatool.R

/**
 * elo Rafał on 30/07/2017.
 */
class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        val notification = p0?.notification
        val title = notification?.title
        val content = notification?.body
        // unfortunately you cannot make custom notification for firebase :(
        // so yeah makeNotification is unused, maybe I'll try something later
        // makeNotification(title as String, content as String)
    }

    /**
     * unused unfortunately :(
     */
    private fun makeNotification(notificationTitle: String, notificationBody: String) {
        val notifMgr: NotificationManager? = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        val vibrationArray: LongArray = longArrayOf(200, 500, 200, 500, 200, 500, 200)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id: String = "faja_channel"
            val name: CharSequence = getString(R.string.channel_name)
            val description: String = "No palimy papierosy.";
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT

            val channel: NotificationChannel = NotificationChannel(id, name, importance)
            channel.description = description
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.vibrationPattern = vibrationArray

            notifMgr?.createNotificationChannel(channel)
        }
        val notifBuilder = NotificationCompat.Builder(this)

        notifBuilder.setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setVibrate(vibrationArray)

        //TODO : add actions 'going' and 'not going'

        notifMgr?.notify(123, notifBuilder.build())

    }
}
