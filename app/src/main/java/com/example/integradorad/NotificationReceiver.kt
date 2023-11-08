package com.example.integradorad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val medicationName = intent?.getStringExtra("medication_name")

        val builder = NotificationCompat.Builder(context!!, "medication_notification")
            .setSmallIcon(R.drawable.ic_medication)
            .setContentTitle("Recordatorio de Medicamento")
            .setContentText("Toma tu medicamento: $medicationName")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())

    }
}