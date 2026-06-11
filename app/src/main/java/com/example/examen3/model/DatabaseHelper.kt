package com.example.examen3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "examen3.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_JUGADORES = "jugadores"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_CONTRASENA = "contrasena"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_JUGADORES (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT NOT NULL UNIQUE,
                $COL_CONTRASENA TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_JUGADORES")
        onCreate(db)
    }

    fun insertarJugador(nombre: String, contrasena: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_CONTRASENA, contrasena)
        }
        return try {
            db.insertOrThrow(TABLE_JUGADORES, null, values)
        } catch (e: Exception) {
            -1L
        }
    }

    fun validarCredenciales(nombre: String, contrasena: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_JUGADORES, arrayOf(COL_ID),
            "$COL_NOMBRE = ? AND $COL_CONTRASENA = ?",
            arrayOf(nombre, contrasena),
            null, null, null
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

    fun existeJugador(nombre: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_JUGADORES, arrayOf(COL_ID),
            "$COL_NOMBRE = ?", arrayOf(nombre),
            null, null, null
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }
}