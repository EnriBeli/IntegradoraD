package com.example.integradorad

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistrarCitaActivity : AppCompatActivity() {
    private lateinit var UidUsuarioTextView: TextView
    private lateinit var CorreousuarioTextView: TextView
    private lateinit var FechahoraactualTextView: TextView

    private lateinit var nombreEditText: EditText
    private lateinit var tituloEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var fechaTextView: TextView
    private lateinit var horaTextView: TextView
    private lateinit var EstadoTextView: TextView

    private lateinit var guardarButton: Button
    private lateinit var databaseReference: DatabaseReference

    private lateinit var btnHora: Button
    private lateinit var textViewHora: TextView
    private lateinit var textViewFecha: TextView
    private lateinit var btnCalendario: Button
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cita)

        textViewFecha = findViewById(R.id.Fecha)

        btnCalendario = findViewById(R.id.Btn_Calendario)

        btnCalendario.setOnClickListener {
            showDatePickerDialog()
        }

        btnHora = findViewById(R.id.Btn_Hora)
        textViewHora = findViewById(R.id.Hora)

        btnHora.setOnClickListener {
            showTimePickerDialog()
        }

        obtenerFechaHoraActual()

        val cancelarButton = findViewById<Button>(R.id.Btn_Cancelar)


        // Obtén una referencia a Firebase Authentication
        val auth = FirebaseAuth.getInstance()

        // Obtén una referencia a Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val usuariosRef = database.getReference("Usuarios")

        // Obtén el usuario actual
        val user = auth.currentUser

        if (user != null) {
            // Obten el ID del usuario actual
            val userId = user.uid

            // Obtén una referencia al nodo "Usuarios" y al nodo del usuario actual
            val usuarioActualRef = usuariosRef.child(userId)

            // Escucha los cambios en los datos del usuario actual
            usuarioActualRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val usuario = dataSnapshot.getValue(MainActivity.Usuario::class.java)

                        if (usuario != null) {
                            val uidPrincipallTextView = findViewById<TextView>(R.id.Uid_Usuario)
                            val correoPrincipalTextView = findViewById<TextView>(R.id.Correo_usuario)

                            // Actualiza los TextViews con los datos del usuario
                            uidPrincipallTextView.text = user.uid
                            correoPrincipalTextView.text = user.email
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejar errores, si es necesario
                }
            })
        }

        // Inicializa Firebase Database
        val database1 = FirebaseDatabase.getInstance()
        databaseReference = database1.reference.child("citas_usuario") // Reemplaza "citas" con el nombre de tu rama

        UidUsuarioTextView = findViewById(R.id.Uid_Usuario)
        CorreousuarioTextView = findViewById(R.id.Correo_usuario)
        FechahoraactualTextView = findViewById(R.id.Fecha_hora_actual)

        nombreEditText = findViewById(R.id.Nombre)
        tituloEditText = findViewById(R.id.Titulo)
        descripcionEditText = findViewById(R.id.Descripcion)
        fechaTextView = findViewById(R.id.Fecha)
        horaTextView = findViewById(R.id.Hora)
        EstadoTextView = findViewById(R.id.Estado)

        guardarButton = findViewById(R.id.Btn_Guardar_datos)

        guardarButton.setOnClickListener {
            val Uid_Usuario = UidUsuarioTextView .text.toString().trim()
            val Correo_usuario = CorreousuarioTextView .text.toString().trim()
            val Fecha_hora_actual = FechahoraactualTextView .text.toString().trim()
            val nombre = nombreEditText.text.toString().trim()
            val titulo = tituloEditText.text.toString().trim()
            val descripcion = descripcionEditText.text.toString().trim()
            val fecha = fechaTextView.text.toString().trim()
            val hora = horaTextView.text.toString().trim()
            val Estado = EstadoTextView.text.toString().trim()


            if (titulo.isNotEmpty() && descripcion.isNotEmpty() && fecha.isNotEmpty() && hora.isNotEmpty()) {
                // Todos los campos están llenos, puedes proceder a guardar los datos en Firebase
                val nuevaCitaId = databaseReference.push().key
                if (nuevaCitaId != null) {
                    val nuevaCita = Cita(
                        Uid_Usuario,
                        Correo_usuario,
                        Fecha_hora_actual,
                        nombre,
                        titulo,
                        descripcion,
                        fecha,
                        hora,
                        Estado
                    )
                    databaseReference.child(nuevaCitaId).setValue(nuevaCita)
                    // Muestra un mensaje de éxito
                    Toast.makeText(this, "Cita agendada exitosamente", Toast.LENGTH_SHORT).show()

                    // Redirige al usuario a la actividad principal (MainActivity)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Esto cierra la actividad actual para que el usuario no pueda regresar
                }
                } else {
                    Toast.makeText(this, "No se pudo crear . Vuelva a intentarlo", Toast.LENGTH_SHORT).show()
                    // Muestra un msdasdensaje de error o realiza la validación según sea necesario
                }
            }

        cancelarButton.setOnClickListener {
            // Redirige a tu MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual
        }


    }

    data class Cita(
        val uidUsuario: String,
        val correoUsuario: String,
        val fechaHoraActual: String,
        val nombre: String,
        val titulo: String,
        val descripcion: String,
        val fecha: String,
        val hora: String,
        val estado: String
    )



    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Aquí puedes manejar la fecha seleccionada y actualizar el TextView
            val selectedDate = "$year-${month + 1}-$dayOfMonth" // El mes es 0-based, por lo que sumamos 1
            textViewFecha.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val isAM = hourOfDay < 12
            val amPm: String = if (isAM) "AM" else "PM"

            val selectedHour = if (hourOfDay == 0 || hourOfDay == 12) 12 else hourOfDay % 12
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", selectedHour, minute, amPm)
            textViewHora.text = selectedTime
        }, hour, minute, false)

        timePickerDialog.show()
    }

    private fun obtenerFechaHoraActual() {
        val formato = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault())
        val fechaHoraRegistro = formato.format(System.currentTimeMillis())
        val Fecha_hoara_actualTextView = findViewById<TextView>(R.id.Fecha_hora_actual)
        Fecha_hoara_actualTextView.text = fechaHoraRegistro
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cita, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Agregar_Nota_BD -> {
                Toast.makeText(this, "Nota Agregada con Éxito", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}