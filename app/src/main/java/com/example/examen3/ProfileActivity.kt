package com.example.examen3

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvNombreJugador: TextView
    private lateinit var tvUltimaConexion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvNombreJugador  = findViewById(R.id.tvNombreJugador)
        tvUltimaConexion = findViewById(R.id.tvUltimaConexion)

        val nombre = intent.getStringExtra("NOMBRE_JUGADOR") ?: "Jugador"
        tvNombreJugador.text = nombre

        val prefs = getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
        val ultimaConexion = prefs.getString("ultima_conexion_$nombre", null)

        tvUltimaConexion.text = if (ultimaConexion != null) {
            "Última conexión: $ultimaConexion"
        } else {
            "Última conexión: Primera vez"
        }

        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val ahora   = formato.format(Date())
        prefs.edit().putString("ultima_conexion_$nombre", ahora).apply()
    }
}