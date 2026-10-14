package com.example.freegames.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
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

    private lateinit var binding: ActivityMainBinding
    private lateinit var gameAdapter: GameAdapter

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
        fetchGames()
    }

    private fun setupRecyclerView() {
        gameAdapter = GameAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }
    }

    private fun fetchGames() {
        val api = FreeToGameApi.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val gamesList = api.getGamesList()
                withContext(Dispatchers.Main) {
                    gameAdapter.updateData(gamesList)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching games", e)
            }
        }
    }
}
