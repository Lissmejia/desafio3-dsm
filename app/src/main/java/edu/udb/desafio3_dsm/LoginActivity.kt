package edu.udb.desafio3_dsm

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DBHelper(this)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)

        btnIniciarSesion.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString()

            // Validación básica
            if (usuario.isEmpty()) {
                etUsuario.error = "Ingrese su correo"
                etUsuario.requestFocus()
                return@setOnClickListener
            }

            // Validación de formato de correo (opcional, si el usuario es un email)
            if (!Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
                etUsuario.error = "Correo inválido"
                etUsuario.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Ingrese su contraseña"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            // Verificación en la base de datos
            if (db.verificarUsuario(usuario, password)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}
