package edu.udb.desafio3_dsm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        dbHelper = DBHelper(this)

        val etCorreoRegistro = findViewById<EditText>(R.id.etCorreoRegistro)
        val etContrasenaRegistro = findViewById<EditText>(R.id.etContrasenaRegistro)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etConfirmarContrasena)
        val btnRegistrarUsuario = findViewById<Button>(R.id.btnRegistrarUsuario)
        val tvVolverLogin = findViewById<TextView>(R.id.tvVolverLogin)

        btnRegistrarUsuario.setOnClickListener {
            val correo = etCorreoRegistro.text.toString().trim()
            val contrasena = etContrasenaRegistro.text.toString().trim()
            val confirmar = etConfirmarContrasena.text.toString().trim()

            when {
                correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty() -> {
                    Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                    Toast.makeText(this, "Correo inválido.", Toast.LENGTH_SHORT).show()
                }

                contrasena.length < 6 -> {
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                }

                contrasena != confirmar -> {
                    Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
                }

                dbHelper.usuarioExiste(correo) -> {
                    Toast.makeText(this, "Este correo ya está registrado.", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val exito = dbHelper.insertarUsuario(correo, contrasena)
                    if (exito) {
                        Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvVolverLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
