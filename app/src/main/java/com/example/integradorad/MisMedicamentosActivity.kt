package com.example.integradorad

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Calendar

class MisMedicamentosActivity : AppCompatActivity() {

    private lateinit var medicationNameEditText: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var addMedicationButton: Button
    private val REQUEST_SCHEDULE_EXACT_ALARM_PERMISSION = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_medicamentos)



        medicationNameEditText = findViewById(R.id.medicationNameEditText)
        timePicker = findViewById(R.id.timePicker)
        addMedicationButton = findViewById(R.id.addMedicationButton)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        addMedicationButton.setOnClickListener {
            val medicationName = medicationNameEditText.text.toString()
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute

            scheduleNotification(alarmManager, medicationName, selectedHour, selectedMinute)
        }

    }


    private fun scheduleNotification(
        alarmManager: AlarmManager,
        medicationName: String,
        selectedHour: Int,
        selectedMinute: Int
    ) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
        calendar.set(Calendar.MINUTE, selectedMinute)
        calendar.set(Calendar.SECOND, 0)

        val intent = Intent(this, NotificationReceiver::class.java)
        intent.putExtra("medication_name", medicationName)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE  // Agrega esta línea
        )

        // Configura la notificación para que se repita diariamente
        val triggerTime = calendar.timeInMillis
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )

        Toast.makeText(this, "Notificación programada", Toast.LENGTH_SHORT).show()
    }





}