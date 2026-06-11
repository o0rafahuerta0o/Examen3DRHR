package com.example.examen3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var etNombreUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnRegistrarse: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbHelper = DatabaseHelper(this)

        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etContrasena    = findViewById(R.id.etContrasena)
        btnRegistrarse  = findViewById(R.id.btnRegistrarse)

        btnRegistrarse.setOnClickListener {
            val nombre     = etNombreUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (nombre.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultado = dbHelper.insertarJugador(nombre, contrasena)
            if (resultado != -1L) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "El usuario ya está registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}