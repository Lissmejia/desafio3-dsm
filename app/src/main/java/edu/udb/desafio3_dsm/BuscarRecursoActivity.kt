package edu.udb.desafio3_dsm

import RecursoAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BuscarRecursoActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private lateinit var recycler: RecyclerView
    private var recursoEncontrado: Recurso? = null
    private lateinit var btnEditarRecurso: FloatingActionButton
    private lateinit var btnEliminarRecurso: FloatingActionButton

    private var recursoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_recurso)

        db = DBHelper(this)
        recycler = findViewById(R.id.recyclerResultado)
        recycler.layoutManager = LinearLayoutManager(this)

        btnEditarRecurso = findViewById(R.id.btnEditarRecurso)
        btnEliminarRecurso = findViewById(R.id.btnEliminarRecurso)

        val idRecurso = intent.getStringExtra("ID_RECURSO")

        if (idRecurso != null) {
            recursoId = idRecurso.toInt()
            val recurso = db.buscarRecursoPorId(recursoId)
            recursoEncontrado = recurso
            if (recurso != null) {
                recycler.adapter = RecursoAdapter(listOf(recurso), db) {
                    finish()
                }
            } else {
                Toast.makeText(this, "Recurso no encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnEditarRecurso.setOnClickListener {
            recursoEncontrado?.let {
                val intent = Intent(this, ModificarRecursoActivity::class.java)
                intent.putExtra("ID_RECURSO", it.id) // pasar como Int
                startActivity(intent)
            }
        }

        btnEliminarRecurso.setOnClickListener {
            recursoEncontrado?.let {
                val filas = db.eliminarRecurso(it.id)
                if (filas > 0) {
                    Toast.makeText(this, "Recurso eliminado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al eliminar recurso", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (recursoId != -1) {
            val recursoActualizado = db.buscarRecursoPorId(recursoId)
            if (recursoActualizado != null) {
                recursoEncontrado = recursoActualizado
                recycler.adapter = RecursoAdapter(listOf(recursoActualizado), db) {
                    finish()
                }
            } else {
                Toast.makeText(this, "El recurso ha sido eliminado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
