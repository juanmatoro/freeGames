package com.example.freegames.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freegames.R
import com.example.freegames.adapters.GameAdapter
import com.example.freegames.data.FreeToGameApi
import com.example.freegames.data.Game
import com.example.freegames.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // Almacena la instancia de enlace generada por ViewBinding para 'activity_main.xml'.
    private lateinit var binding: ActivityMainBinding

    // Almacena la instancia del adaptador ('GameAdapter').
    private lateinit var gameAdapter: GameAdapter

    // Almacena la lista completa de juegos descargados para permitir filtrado local en vivo mediante el buscador.
    private var allGames: List<Game> = emptyList()

    private var selectedPlatform: String? = null
    private var selectedCategory: String? = null

    // Variables para el scroll infinito (paginación por lotes de 20 en 20)
    private var displayedGamesCount = 20

    // Punto de entrada principal de la Actividad.
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
        setupInfiniteScroll()
        setupSearch()
        setupPlatformFilters()
        setupGenreChips()
        setupBottomNavigation()

        // Carga inicial: todos los juegos disponibles sin filtros ni selecciones previas.
        fetchGames(null, null)
    }

    // Configura el RecyclerView y el adaptador (GameAdapter).
    private fun setupRecyclerView() {
        gameAdapter = GameAdapter(emptyList()) { game ->
            // ¿Qué hacemos aquí? Usamos la constante estática (GameDetailActivity.EXTRA_GAME_ID)
            // en lugar de escribir una cadena de texto ("EXTRA_GAME_ID") manualmente.
            // ¿Por qué? Evita errores de tipeo (typos) y centraliza la clave del Intent en la actividad receptora.
            val intent = Intent(this, GameDetailActivity::class.java).apply {
                putExtra(GameDetailActivity.EXTRA_GAME_ID, game.id)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }
    }

    // Configura el listener de scroll en el RecyclerView para lograr scroll infinito (paginación progresiva).
    private fun setupInfiniteScroll() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Si el usuario llega a los últimos 3 elementos, cargamos el siguiente lote
                if (dy > 0 && lastVisibleItem >= totalItemCount - 3) {
                    loadMoreGames()
                }
            }
        })
    }

    // Incrementa el contador de elementos visibles y actualiza el adaptador con el siguiente lote.
    private fun loadMoreGames() {
        if (displayedGamesCount < allGames.size) {
            displayedGamesCount += 20
            filterAndDisplayGames(binding.etSearch.text.toString())
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                displayedGamesCount = 20 // Reiniciar paginación al buscar
                filterAndDisplayGames(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupPlatformFilters() {
        val pills = listOf(binding.pillTodo, binding.pillPc, binding.pillWeb)
        val platforms = listOf(null, "pc", "browser")

        pills.forEachIndexed { index, pill ->
            pill.setOnClickListener {
                selectedPlatform = platforms[index]
                updatePlatformPillUI(pills, pill)
                fetchGames(selectedCategory, selectedPlatform)
            }
        }
    }

    private fun updatePlatformPillUI(pills: List<TextView>, activePill: TextView) {
        pills.forEach { p ->
            if (p == activePill) {
                p.setBackgroundResource(R.drawable.bg_pill_active)
                p.setTextColor(Color.parseColor("#0F131C"))
            } else {
                p.setBackgroundResource(R.drawable.bg_pill_inactive)
                p.setTextColor(Color.parseColor("#94A3B8"))
            }
        }
    }

    private fun setupGenreChips() {
        val chips = listOf(binding.chipAll, binding.chipMmorpg, binding.chipShooter, binding.chipMoba, binding.chipStrategy)
        val categories = listOf(null, "mmorpg", "shooter", "moba", "strategy")

        chips.forEachIndexed { index, chip ->
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    chips.filter { it != chip }.forEach { it.isChecked = false }
                    selectedCategory = categories[index]
                    fetchGames(selectedCategory, selectedPlatform)
                }
            }
        }
    }

    // Filtra la lista completa y aplica paginación (take(displayedGamesCount)) para el scroll infinito.
    private fun filterAndDisplayGames(query: String) {
        val filteredList = if (query.isBlank()) {
            allGames
        } else {
            allGames.filter { it.title.contains(query, ignoreCase = true) }
        }
        val pagedList = filteredList.take(displayedGamesCount)
        gameAdapter.updateData(pagedList)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    selectedCategory = null
                    selectedPlatform = null
                    updatePlatformPillUI(listOf(binding.pillTodo, binding.pillPc, binding.pillWeb), binding.pillTodo)
                    binding.chipAll.isChecked = false
                    fetchGames(null, null)
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

    private fun showCategoriesDialog() {
        val categories = arrayOf("Todos", "MMORPG", "Shooter", "MOBA", "Strategy", "Racing", "Sports")
        val categoryValues = arrayOf(null, "mmorpg", "shooter", "moba", "strategy", "racing", "sports")

        AlertDialog.Builder(this)
            .setTitle("Seleccionar Categoría")
            .setItems(categories) { _, which ->
                selectedCategory = categoryValues[which]
                binding.etSearch.setText("")
                fetchGames(selectedCategory, selectedPlatform)
                Toast.makeText(this, "Filtro: ${categories[which]}", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun fetchGames(category: String?, platform: String?) {
        val api = FreeToGameApi.getInstance()
        displayedGamesCount = 20 // Reiniciar contador en cada nueva carga

        CoroutineScope(Dispatchers.IO).launch {
            try {
                allGames = api.getGamesList(category, platform)
                withContext(Dispatchers.Main) {
                    filterAndDisplayGames(binding.etSearch.text.toString())
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
