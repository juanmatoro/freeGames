package com.example.freegames.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freegames.R
import com.example.freegames.adapters.GameAdapter
import com.example.freegames.data.FreeToGameApi
import com.example.freegames.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // Almacena la instancia de enlace generada por ViewBinding para 'activity_main.xml'.
    // Permite acceder a todas las vistas de la pantalla (Toolbar, RecyclerView, BottomNavigationView) de forma segura y sin findViewById.
    private lateinit var binding: ActivityMainBinding

    // Almacena la instancia del adaptador ('GameAdapter') encargado de gestionar,
    // reciclar y pintar la lista de juegos en el RecyclerView, así como gestionar los clics de las tarjetas.
    private lateinit var gameAdapter: GameAdapter

    // Punto de entrada principal de la Actividad.
    // Configura la UI, ViewBinding, RecyclerView, menú inferior y carga inicial de datos.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupBottomNavigation()
        fetchGames(null)
    }

    // Configura el RecyclerView y el adaptador (GameAdapter).
    // Define la acción de clic (Lambda) para abrir GameDetailActivity pasando los datos del juego mediante Intent.
    private fun setupRecyclerView() {
        gameAdapter = GameAdapter(emptyList()) { game ->
            val intent = Intent(this, GameDetailActivity::class.java).apply {
                putExtra("EXTRA_GAME_ID", game.id)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }
    }

    // Configura el menú de navegación inferior (BottomNavigationView) alineado al diseño de screen.png (Explorar, Categorías, Regalos, Guardados).
    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    fetchGames(null)
                    true
                }
                R.id.nav_categories -> {
                    showCategoriesDialog()
                    true
                }
                R.id.nav_giveaways -> {
                    Toast.makeText(this, "Monitor de Giveaways y Botines - Próximamente", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_saved -> {
                    Toast.makeText(this, "Biblioteca de Favoritos y Deseos - Próximamente", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    // Muestra un diálogo selector de categorías temáticas.
    private fun showCategoriesDialog() {
        val categories = arrayOf("Todos", "MMORPG", "Shooter", "MOBA", "Strategy", "Racing", "Sports")
        val categoryValues = arrayOf(null, "mmorpg", "shooter", "moba", "strategy", "racing", "sports")

        AlertDialog.Builder(this)
            .setTitle("Seleccionar Categoría")
            .setItems(categories) { _, which ->
                val selectedCategory = categoryValues[which]
                fetchGames(selectedCategory)
                Toast.makeText(this, "Filtro: ${categories[which]}", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    // Realiza la petición de red a la API mediante Retrofit y Corrutinas.
    // Se ejecuta en segundo plano (Dispatchers.IO) y actualiza la lista en la UI (Dispatchers.Main).
    private fun fetchGames(category: String?) {
        val api = FreeToGameApi.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gamesList = api.getGamesList(category)
                withContext(Dispatchers.Main) {
                    gameAdapter.updateData(gamesList)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching games", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error al cargar juegos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
