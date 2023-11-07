package com.example.integradorad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*

class MisCitasActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_citas)
        // Obtén una referencia al usuario actual de Firebase
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userUid = currentUser.uid
            val citasUsuarioRef: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child("citas_usuario")
                .child(userUid)

            val layoutCitas: LinearLayout = findViewById(R.id.LayoutCitas)

            citasUsuarioRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    layoutCitas.removeAllViews()

                    for (citaSnapshot in dataSnapshot.children) {
                        val correoUsuario: String? = citaSnapshot.child("correoUsuario").getValue(String::class.java)
                        val descripcion: String? = citaSnapshot.child("descripcion").getValue(String::class.java)
                        val estado: String? = citaSnapshot.child("estado").getValue(String::class.java)
                        val fecha: String? = citaSnapshot.child("fecha").getValue(String::class.java)
                        val hora: String? = citaSnapshot.child("hora").getValue(String::class.java)
                        val titulo: String? = citaSnapshot.child("titulo").getValue(String::class.java)
                        val uidUsuario: String? = citaSnapshot.child("uidUsuario").getValue(String::class.java)

                        // Crear una vista para mostrar la cita
                        val citaView = layoutInflater.inflate(R.layout.activity_mis_citas, null)

                        val correoUsuarioTextView: TextView = citaView.findViewById(R.id.uidUsuariocitas_item)
                        correoUsuarioTextView.text = correoUsuario

                        val descripcionTextView: TextView = citaView.findViewById(R.id.descripcion_item)
                        descripcionTextView.text = descripcion

                        val estadoTextView: TextView = citaView.findViewById(R.id.estodo_item)
                        estadoTextView.text = estado

                        val fechaTextView: TextView = citaView.findViewById(R.id.fecha_item)
                        fechaTextView.text = fecha

                        val horaTextView: TextView = citaView.findViewById(R.id.hora_item)
                        horaTextView.text = hora

                        val tituloTextView: TextView = citaView.findViewById(R.id.titulo_item)
                        tituloTextView.text = titulo

                        val uidUsuarioTextView: TextView = citaView.findViewById(R.id.uidUsuariocitas_item)
                        uidUsuarioTextView.text = uidUsuario

                        layoutCitas.addView(citaView)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejo de errores, si es necesario
                }
            })
        }


    }
    class Cita(
        val correoUsuario: String? = null,
        val descripcion: String? = null,
        val estado: String? = null,
        val fecha: String? = null,
        val fechaHoraActual: String? = null,
        val hora: String? = null,
        val titulo: String? = null,
        val uidUsuario: String? = null
    )




    class CitaAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {
        class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val correoUsuario: TextView = itemView.findViewById(R.id.correousuariocitas_item)
            val descripcion: TextView = itemView.findViewById(R.id.descripcion_item)
            val estado: TextView = itemView.findViewById(R.id.estodo_item)
            val fecha: TextView = itemView.findViewById(R.id.fecha_item)
            val fechaHoraActual: TextView = itemView.findViewById(R.id.fechaHoraActual_item)
            val hora: TextView = itemView.findViewById(R.id.hora_item)
            val titulo: TextView = itemView.findViewById(R.id.titulo_item)
            val uidUsuario: TextView = itemView.findViewById(R.id.uidUsuariocitas_item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_mis_citas, parent, false)
            return CitaViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
            val currentCita = citas[position]
            holder.correoUsuario.text = currentCita.correoUsuario
            holder.descripcion.text = currentCita.descripcion
            holder.estado.text = currentCita.estado
            holder.fecha.text = currentCita.fecha
            holder.fechaHoraActual.text = currentCita.fechaHoraActual
            holder.hora.text = currentCita.hora
            holder.titulo.text = currentCita.titulo
            holder.uidUsuario.text = currentCita.uidUsuario
        }

        override fun getItemCount(): Int {
            return citas.size
        }
    }


}

