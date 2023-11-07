package com.example.integradorad
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.integradorad.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signUpButton.setOnClickListener {

            val mNombre = binding.NombreEditText.text.toString()
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()
            val mRepeatPassword = binding.repeatPasswordEditText.text.toString()

            val passwordRegex = Pattern.compile("^" +
                    "(?=.*[-@#$%^&+=])" +     // Al menos 1 carácter especial
                    ".{6,}" +                // Al menos 4 caracteres
                    "$")
            if (TextUtils.isEmpty(mNombre)){
                Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
            }
            else if(mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                Toast.makeText(this, "Ingrese un email valido.",
                    Toast.LENGTH_SHORT).show()
            } else if (mPassword.isEmpty() || !passwordRegex.matcher(mPassword).matches()){
                Toast.makeText(this, "La contraseña es debil.",
                    Toast.LENGTH_SHORT).show()
            } else if (mPassword != mRepeatPassword){
                Toast.makeText(this, "Confirma la contraseña.",
                    Toast.LENGTH_SHORT).show()
            } else {
                createAccount(mEmail, mPassword)
            }

        }

        binding.SesionInico.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        binding.TeminosCondiciones.setOnClickListener {
            val intent = Intent(this, TerminosCondicionesActivity::class.java)
            this.startActivity(intent)
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            } else {
                val intent = Intent(this, CheckEmailActivity::class.java)
                this.startActivity(intent)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Obtener el ID del usuario
                        val userId = user.uid

                        // Crear un objeto que contiene los datos del usuario
                        val userData = HashMap<String, Any>()
                        userData["uid"] = userId
                        userData["nombre"] = binding.NombreEditText.text.toString()
                        userData["correo"] = email
                        userData["password"] = password

                        // Guardar los datos del usuario en la base de datos Realtime Database
                        val databaseReference = FirebaseDatabase.getInstance().reference
                        databaseReference.child("Usuarios").child(userId).setValue(userData)

                        // Redirigir a la actividad de verificación de correo
                        val intent = Intent(this, CheckEmailActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error al obtener el usuario actual", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "No se pudo crear la cuenta. Vuelva a intentarlo", Toast.LENGTH_SHORT).show()
                }
            }
    }

}