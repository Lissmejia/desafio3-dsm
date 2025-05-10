package edu.udb.desafio3_dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCRUDRecursos = findViewById<Button>(R.id.btnCRUDRecursos)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        btnCRUDRecursos.setOnClickListener {
            startActivity(Intent(this, CRUDMenuActivity::class.java)) // Asegúrate de tener esta actividad
        }

        btnCerrarSesion.setOnClickListener {
            finish()
        }
    }
}
