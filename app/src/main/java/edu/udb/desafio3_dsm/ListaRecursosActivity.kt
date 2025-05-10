package edu.udb.desafio3_dsm

import RecursoAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaRecursosActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private lateinit var recycler: RecyclerView

    override fun onResume() {
        super.onResume()
        cargarLista()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_recursos)

        db = DBHelper(this)
        recycler = findViewById(R.id.recyclerRecursos)
        recycler.layoutManager = LinearLayoutManager(this)

        cargarLista()
    }

    private fun cargarLista() {
        val lista = db.obtenerTodos()
        recycler.adapter = RecursoAdapter(lista, db) {
            cargarLista()
        }
    }
}
