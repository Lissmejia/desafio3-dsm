package edu.udb.desafio3_dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CRUDMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnVerLista = findViewById<Button>(R.id.btnVerLista)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val etBuscarPorId = findViewById<EditText>(R.id.etBuscarPorId)

        btnVerLista.setOnClickListener {
            startActivity(Intent(this, ListaRecursosActivity::class.java))
        }

        btnBuscar.setOnClickListener {
            val id = etBuscarPorId.text.toString().trim()

            if (id.isNotEmpty()) {
                val intent = Intent(this, BuscarRecursoActivity::class.java).apply {
                    putExtra("ID_RECURSO", id)
                }
                startActivity(intent)
            } else {
                etBuscarPorId.error = "Por favor ingresa un ID"
            }
        }

        // Acción para agregar un nuevo recurso
        btnAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarRecursoActivity::class.java))
        }
    }
}
