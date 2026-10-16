package com.example.freegames.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeToGameApi {

    // Endpoint para obtener la lista de juegos con filtros opcionales de categoría y plataforma.
    @GET("games")
    suspend fun getGamesList(
        @Query("category") category: String? = null,
        @Query("platform") platform: String? = null
    ): List<Game>

    // Endpoint para obtener los detalles completos de un videojuego específico buscando por su ID.
    @Suppress("unused")
    @GET("game")
    suspend fun getGameById(@Query("id") id: Int): GameDetail

    companion object {
        // Patrón Factory / Singleton para crear e inicializar la instancia única de Retrofit
        // configurada con la URL base de FreeToGame y el conversor de Gson.
        fun getInstance(): FreeToGameApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.freetogame.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(FreeToGameApi::class.java)
        }
    }
}
