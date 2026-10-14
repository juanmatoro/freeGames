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

    private lateinit var binding: ActivityGameDetailBinding
    private var gameProfileUrl: String? = null

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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detalle del Juego"

        val gameId = intent.getIntExtra("EXTRA_GAME_ID", -1)
        if (gameId != -1) {
            fetchGameDetail(gameId)
        } else {
            Toast.makeText(this, "ID de juego no válido", Toast.LENGTH_SHORT).show()
        }

        binding.btnGetGame.setOnClickListener {
            gameProfileUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } ?: Toast.makeText(this, "Enlace no disponible", Toast.LENGTH_SHORT).show()
        }
    }

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

    private fun bindGameDetail(detail: GameDetail) {
        gameProfileUrl = detail.profileUrl

        binding.tvDetailTitle.text = detail.title
        binding.tvDetailMetadata.text = "${detail.genre ?: "Género N/D"} • ${detail.platform ?: "Plataforma N/D"}"
        binding.ivDetailThumbnail.load(detail.thumbnail) {
            crossfade(enable = true)
        }

        binding.tvDetailDescription.text = detail.description ?: detail.shortDescription

        // Key Specs
        binding.tvKeySpecs.text = """
            • Desarrollador: ${detail.developer ?: "N/D"}
            • Editor: ${detail.publisher ?: "N/D"}
            • Fecha de Lanzamiento: ${detail.releaseDate ?: "N/D"}
            • Plataforma: ${detail.platform ?: "N/D"}
        """.trimIndent()

        // Minimum System Requirements with null-data resilience
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
