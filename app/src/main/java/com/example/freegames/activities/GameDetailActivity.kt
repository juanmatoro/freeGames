package com.example.freegames.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.freegames.R
import com.example.freegames.data.FreeToGameApi
import com.example.freegames.data.GameDetail
import com.example.freegames.databinding.ActivityGameDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailActivity : AppCompatActivity() {

    // Companion Object para almacenar constantes estáticas (como las claves de los Intent Extras).
    // Esto evita usar "strings mágicos" dispersos por el código y previene errores de tipeo.
    companion object {
        const val EXTRA_GAME_ID = "EXTRA_GAME_ID"
    }

    // Instancia de ViewBinding para acceder a las vistas del layout 'activity_game_detail.xml' sin findViewById.
    private lateinit var binding: ActivityGameDetailBinding

    // Almacena la URL oficial del perfil del juego en FreeToGame para abrirla en el navegador al pulsar el botón de descarga.
    private var gameProfileUrl: String? = null

    // Punto de entrada de la Actividad de Detalle.
    // Configura el diseño Edge-to-Edge, inicializa la Toolbar con retroceso y recupera el ID del juego enviado por Intent.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura la barra superior (Toolbar) como ActionBar con botón de retroceso (Up Button).
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detalle del Juego"

        // Recupera el ID del juego pasado como extra desde MainActivity usando la constante estática.
        val gameId = intent.getIntExtra(EXTRA_GAME_ID, -1)
        if (gameId != -1) {
            fetchGameDetail(gameId)
        } else {
            Toast.makeText(this, "ID de juego no válido", Toast.LENGTH_SHORT).show()
        }

        // Configura el botón flotante inferior (Sticky CTA) para abrir el enlace de descarga oficial en el navegador externo.
        binding.btnGetGame.setOnClickListener {
            gameProfileUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } ?: Toast.makeText(this, "Enlace no disponible", Toast.LENGTH_SHORT).show()
        }
    }

    // Realiza la petición HTTP mediante Retrofit (endpoint /game?id=...) en segundo plano (Dispatchers.IO).
    private fun fetchGameDetail(id: Int) {
        val api = FreeToGameApi.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val detail = api.getGameById(id)
                withContext(Dispatchers.Main) {
                    bindGameDetail(detail)
                }
            } catch (e: Exception) {
                Log.e("GameDetailActivity", "Error fetching game detail", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GameDetailActivity, "Error al cargar detalles", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Vincula los datos detallados del juego a los componentes visuales de la interfaz de usuario.
    private fun bindGameDetail(detail: GameDetail) {
        gameProfileUrl = detail.profileUrl

        binding.tvDetailTitle.text = detail.title
        binding.tvDetailMetadata.text = "${detail.genre ?: "Género N/D"} • ${detail.platform ?: "Plataforma N/D"}"
        
        // Carga la imagen de portada de forma asíncrona y eficiente usando Coil con animación de desvanecimiento (crossfade).
        binding.ivDetailThumbnail.load(detail.thumbnail) {
            crossfade(enable = true)
        }

        binding.tvDetailDescription.text = detail.description ?: detail.shortDescription

        // Ficha técnica: Muestra desarrollador, editor, fecha de lanzamiento y plataforma.
        binding.tvKeySpecs.text = """
            • Desarrollador: ${detail.developer ?: "N/D"}
            • Editor: ${detail.publisher ?: "N/D"}
            • Fecha de Lanzamiento: ${detail.releaseDate ?: "N/D"}
            • Plataforma: ${detail.platform ?: "N/D"}
        """.trimIndent()

        // Requisitos mínimos del sistema con resiliencia ante datos nulos.
        // Si el juego es web o carece de requisitos de hardware, muestra un mensaje amigable explicándolo.
        val reqs = detail.systemRequirements
        if (reqs != null) {
            binding.tvSystemRequirements.text = """
                • OS: ${reqs.os ?: "N/D"}
                • Procesador: ${reqs.processor ?: "N/D"}
                • Memoria: ${reqs.memory ?: "N/D"}
                • Gráficos: ${reqs.graphics ?: "N/D"}
                • Almacenamiento: ${reqs.storage ?: "N/D"}
            """.trimIndent()
        } else {
            binding.tvSystemRequirements.text = "Este título es un juego de navegador web y no requiere especificaciones técnicas mínimas de hardware."
        }
    }

    // Gestiona la acción de la flecha de retroceso en la barra superior para volver a la pantalla anterior.
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
