package com.example.integradorad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.integradorad.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val fileResult = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


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
                        val usuario = dataSnapshot.getValue(Usuario::class.java)

                        if (usuario != null) {
                            val uidPrincipallTextView = findViewById<TextView>(R.id.uidPrincipal)
                            val nombresPrincipalTextView = findViewById<TextView>(R.id.NombresPrincipal)
                            val correoPrincipalTextView = findViewById<TextView>(R.id.CorreoPrincipal)

                            // Actualiza los TextViews con los datos del usuario
                            uidPrincipallTextView.text = "Id: " + user.uid
                            nombresPrincipalTextView.text = "Nombre: " + usuario.nombre
                            correoPrincipalTextView.text = "Correo: " + user.email
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejar errores, si es necesario
                }
            })
        }





        /*updateUI()*/

        /*binding.updateProfileAppCompatButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            updateProfile(name)
        }

        binding.profileImageView.setOnClickListener {
            fileManager()
        }*/
        /*
        binding.deleteAccountTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            this.startActivity(intent)
        }

        binding.updatePasswordTextView.setOnClickListener {
            val intent = Intent(this, UpdatePasswordActivity  ::class.java)
            this.startActivity(intent)
        }*/

        binding.signOutImageView.setOnClickListener {
            signOut()
            finish()
        }

        binding.MiMedicoButton01.setOnClickListener {
            val intent = Intent(this, MiMedico::class.java)
            this.startActivity(intent)
        }

        binding.MisRecetasButton02.setOnClickListener {
            val intent = Intent(this, MisRecetasActivity::class.java)
            this.startActivity(intent)
        }

        binding.MisMedicamentosButton03.setOnClickListener {
            val intent = Intent(this, MisMedicamentosActivity::class.java)
            this.startActivity(intent)
        }

        binding.RegistrarCitaButton04.setOnClickListener {
            val intent = Intent(this, RegistrarCitaActivity::class.java)
            this.startActivity(intent)
        }

        binding.MisCitasButton05.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            this.startActivity(intent)
        }

        binding.UbicacionButton06.setOnClickListener {
            val intent = Intent(this, UbicacionActivity::class.java)
            this.startActivity(intent)
        }

    }

    class Usuario {
        val nombre: String? = null
        val correo: String? = null
    }

    private  fun signOut(){
        auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        this.startActivity(intent)
    }
    /*
        private  fun updateProfile (name : String) {

            val user = auth.currentUser

            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Se realizaron los cambios correctamente.",
                            Toast.LENGTH_SHORT).show()
                        updateUI()
                    }
                }
        }*/

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }
    /*
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data

                uri?.let { imageUpload(it) }

            }
        }
    }*/
    /*
    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se realizaron los cambios correctamente.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }
    */

    /*
    private  fun updateUI () {
        val user = auth.currentUser

        if (user != null){
            binding.emailTextView.text = user.email

            if(user.displayName != null){
                binding.nameTextView.text = user.displayName
                binding.nameEditText.setText(user.displayName)
            }

            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.profileImageView)
            /*Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.bgProfileImageView)*/
        }

    }*/


}


