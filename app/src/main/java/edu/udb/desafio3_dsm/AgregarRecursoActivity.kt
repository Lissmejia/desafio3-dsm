package edu.udb.desafio3_dsm

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AgregarRecursoActivity : AppCompatActivity() {
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_recurso)

        db = DBHelper(this)

        val etId = findViewById<EditText>(R.id.etId)
        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etTipo = findViewById<EditText>(R.id.etTipo)
        val etEnlace = findViewById<EditText>(R.id.etEnlace)
        val etImagen = findViewById<EditText>(R.id.etImagen)

        findViewById<FloatingActionButton>(R.id.btnGuardar).setOnClickListener {
            val idText = etId.text.toString().trim()
            val titulo = etTitulo.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val tipo = etTipo.text.toString().trim()
            val enlace = etEnlace.text.toString().trim()
            val imagen = etImagen.text.toString().trim()

            if (idText.isEmpty() || titulo.isEmpty() || descripcion.isEmpty() || tipo.isEmpty() || enlace.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idText.toInt()
            val exito = db.insertarRecurso(id, titulo, descripcion, tipo, enlace, imagen)

            Toast.makeText(this, if (exito) "Guardado correctamente" else "Error al guardar", Toast.LENGTH_SHORT).show()
            if (exito) finish()
        }
    }
}
