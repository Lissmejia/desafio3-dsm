package edu.udb.desafio3_dsm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, "RecursosDB", null, 3) {

    companion object {
        const val TABLE_USUARIOS = "usuarios"
        const val COLUMN_ID = "id"
        const val COLUMN_USUARIO = "usuario"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE recursos (" +
                    "id INTEGER PRIMARY KEY," +
                    "titulo TEXT," +
                    "descripcion TEXT," +
                    "tipo TEXT," +
                    "enlace TEXT," +
                    "imagen TEXT)"
        )
        Log.d("DBHelper", "Tabla recursos creada")

        db.execSQL(
            "CREATE TABLE $TABLE_USUARIOS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_USUARIO TEXT NOT NULL UNIQUE," +
                    "$COLUMN_PASSWORD TEXT NOT NULL)"
        )
        Log.d("DBHelper", "Tabla usuarios creada")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS recursos")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    fun insertarRecurso(id: Int, titulo: String, descripcion: String, tipo: String, enlace: String, imagen: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", id)
            put("titulo", titulo)
            put("descripcion", descripcion)
            put("tipo", tipo)
            put("enlace", enlace)
            put("imagen", imagen)
        }
        val result = db.insert("recursos", null, values)
        db.close()
        return result != -1L
    }


    fun obtenerTodos(): List<Recurso> {
        val lista = mutableListOf<Recurso>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM recursos", null)

        if (cursor.moveToFirst()) {
            do {
                val recurso = Recurso(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tipo")),
                    cursor.getString(cursor.getColumnIndexOrThrow("enlace")),
                    cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                )
                lista.add(recurso)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    // Insertar un nuevo usuario
    fun insertarUsuario(usuario: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USUARIO, usuario)
        values.put(COLUMN_PASSWORD, password)

        val result = db.insert(TABLE_USUARIOS, null, values)
        db.close()

        // Verificar si el insert fue exitoso
        return result != -1L
    }
    fun verificarUsuario(usuario: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIOS WHERE $COLUMN_USUARIO = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(usuario, password)
        )
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid  // ✅ ESTA LÍNEA ES LA QUE FALTABA
    }

    // Buscar un recurso por su ID
    fun buscarRecursoPorId(id: Int): Recurso? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM recursos WHERE id = ?", arrayOf(id.toString()))

        var recurso: Recurso? = null
        if (cursor.moveToFirst()) {
            recurso = Recurso(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                cursor.getString(cursor.getColumnIndexOrThrow("tipo")),
                cursor.getString(cursor.getColumnIndexOrThrow("enlace")),
                cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            )
        }

        cursor.close()
        db.close()
        return recurso
    }


    // Eliminar un recurso por su ID
    fun eliminarRecurso(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("recursos", "id = ?", arrayOf(id.toString()))
    }


    // Verificar si un usuario ya existe
    fun usuarioExiste(usuario: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIOS WHERE $COLUMN_USUARIO = ?",
            arrayOf(usuario)
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

    fun modificarRecurso(
        id: Int,
        titulo: String,
        descripcion: String,
        tipo: String,
        enlace: String,
        imagen: String?
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("titulo", titulo)
            put("descripcion", descripcion)
            put("tipo", tipo)
            put("enlace", enlace)
            put("imagen", imagen)
        }

        val filasActualizadas = db.update("recursos", contentValues, "id = ?", arrayOf(id.toString()))
        db.close()
        return filasActualizadas > 0
    }

}
