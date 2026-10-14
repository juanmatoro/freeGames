package com.example.freegames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.freegames.data.Game
import com.example.freegames.databinding.ItemGameBinding

class GameAdapter(private var games: List<Game>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.binding.tvTitle.text = game.title
        holder.binding.tvDescription.text = game.shortDescription
        holder.binding.ivThumbnail.load(game.thumbnail) {
            crossfade(enable = true)
        }
    }

    override fun getItemCount(): Int = games.size

    fun updateData(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}
