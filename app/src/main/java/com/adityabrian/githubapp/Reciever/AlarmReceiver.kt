package com.adityabrian.githubapp.Reciever

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.adityabrian.githubapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Github Reminder"
        private const val TIME_FORMAT = "HH:mm"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        /** kita buat fun*/
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val intent = context?.packageManager.getLaunchIntentForPackage("com.adityabrian.githubapp")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        /** kita sen menjadi as NotificationManager
         *  kita buat notification manager */
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /** karena kita membutuhkan channelId nya kita buat companion objectnya*/
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(context.resources.getString((R.string.app_name)))
            .setContentText("Cari user favorit anda sekarang!")
            .setAutoCancel(true)

        /** kita juga ingin set ini untuk android diatasnya, Jadi..*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    /** kemudian kita buat fun buat repeat alarm nya, jadi fun ini dapat diakses dari luar*/
    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        if (isDateInvalid(time, TIME_FORMAT)) return
        /** disini kita melakukan pengecekan dulu*/
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        /** kita tambahkan extra type juga*/

        /** kita lalu buat juga array untuk mensplit jam dan menitnya*/
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        /** kita buat calender disini*/
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        /** kita set untuk jam nya*/
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        /** kit aset untuk menitnya*/
        calendar.set(Calendar.SECOND, 0)
        /** kita set untuk detiknya*/

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)

        /** kita set alarm managernya*/
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, " Repeating Alarm SetUp", Toast.LENGTH_SHORT).show()

        /** kemudian kita buat fun cancel untuk alarmnya*/
    }

    private fun isDateInvalid(time: String, timeFormat: String): Boolean {
        return try {
            val DateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
            DateFormat.isLenient = false
            DateFormat.parse(time)
            return false
        } catch (e: ParseException) {
            true
            /** kembali set repeating alarm*/
        }
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, " Repeating Alarm Canceled!", Toast.LENGTH_SHORT).show()
    }
}