package edu.udb.desafio3_dsm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ModificarRecursoActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    private var recursoId: Int = -1 // Variable para almacenar el ID del recurso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_recurso)

        db = DBHelper(this)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etTipo = findViewById<EditText>(R.id.etTipo)
        val etEnlace = findViewById<EditText>(R.id.etEnlace)
        val etImagen = findViewById<EditText>(R.id.etImagen)

        recursoId = intent.getIntExtra("ID_RECURSO", -1)

        if (recursoId != -1) {
            // Buscar el recurso en la base de datos
            val recurso = db.buscarRecursoPorId(recursoId)
            if (recurso != null) {
                // Si el recurso existe, cargar los datos en los campos
                etTitulo.setText(recurso.titulo)
                etDescripcion.setText(recurso.descripcion)
                etTipo.setText(recurso.tipo)
                etEnlace.setText(recurso.enlace)
                etImagen.setText(recurso.imagen ?: "")
            } else {
                // Si no se encuentra el recurso
                Toast.makeText(this, "Recurso no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Si el ID es inválido o no se pasó
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish() // Cerrar la actividad si no se pasó un ID válido
        }

        findViewById<Button>(R.id.btnModificar).setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()
            val tipo = etTipo.text.toString()
            val enlace = etEnlace.text.toString()
            val imagen = etImagen.text.toString()

            // Verificar que los campos no estén vacíos
            if (titulo.isEmpty() || descripcion.isEmpty() || tipo.isEmpty() || enlace.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val exito = db.modificarRecurso(recursoId, titulo, descripcion, tipo, enlace, imagen)
            if (exito) {
                Toast.makeText(this, "Recurso modificado correctamente", Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad al guardar
            } else {
                Toast.makeText(this, "Error al modificar el recurso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
