package com.example.freegames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.freegames.data.Game
import com.example.freegames.databinding.ItemGameBinding

class GameAdapter(
    // - private var games: Es 'var' porque la lista cambia al filtrar o recargar datos. Es 'private'
    //   para aplicar encapsulamiento: solo el adaptador gestiona su lista interna mediante 'updateData'.
    private var games: List<Game>,
    // - private val onItemClick: Es 'val' porque la acción de clic no cambia. Es 'private' porque
    //   solo el adaptador invoca esta función internamente cuando el usuario toca una tarjeta.
    private val onItemClick: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    // El uso de 'val' convierte el parámetro del constructor en una propiedad pública de la clase.
    // Esto es necesario para poder acceder a 'holder.binding' desde onBindViewHolder
    // y enlazar los datos (título, descripción, imagen) sin usar findViewById.
    class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root)

    // Se ejecuta cuando el RecyclerView necesita crear una nueva celda física.
    // Infla el archivo XML de la tarjeta (item_game.xml) usando ViewBinding.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    // Se ejecuta cada vez que una celda entra en pantalla para mostrar datos.
    // Extrae el juego de la posición actual, pinta las vistas y configura el clic.
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.binding.tvTitle.text = game.title
        holder.binding.tvDescription.text = game.shortDescription
        holder.binding.ivThumbnail.load(game.thumbnail) {
            crossfade(enable = true)
        }
        holder.itemView.setOnClickListener {
            onItemClick(game)
        }
    }

    // Devuelve el número total de elementos que tiene la lista.
    // El RecyclerView usa esto para saber cuántas filas debe calcular y renderizar.
    override fun getItemCount(): Int = games.size

    // Permite actualizar la lista de datos de forma dinámica (ej. al filtrar por categorías)
    // y le ordena al RecyclerView redibujar la pantalla.
    fun updateData(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}
