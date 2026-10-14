# Documento de Corrección de Errores y Advertencias (Lint / Build)

Este documento detalla las advertencias (*warnings*) y posibles incidencias de compilación y análisis estático detectadas al preparar el repositorio para el `git push`, así como su explicación y corrección técnica.

---

## 1. Modificador `inner` redundante en `GameViewHolder`
- **Archivo afectado**: [GameAdapter.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/adapters/GameAdapter.kt)
- **Advertencia**: *Redundant 'inner' modifier.*
- **¿Por qué ocurría?**: En Kotlin, una clase marcada como `inner` retiene una referencia implícita a la instancia de su clase contenedora (`GameAdapter`). Como el `GameViewHolder` no necesita acceder a propiedades de `GameAdapter`, marcarla como `inner` es innecesario y puede causar retención de memoria (*memory leaks*).
- **Corrección**: Se eliminó la palabra reservada `inner`, convirtiéndola en una clase estática anidada tradicional.
  ```kotlin
  // Antes
  inner class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root)
  
  // Después
  class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root)
  ```

---

## 2. Argumento literal booleano sin nombre de parámetro (`crossfade`)
- **Archivo afectado**: [GameAdapter.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/adapters/GameAdapter.kt)
- **Advertencia**: *Boolean literal argument without a parameter name.*
- **¿Por qué ocurría?**: Las guías de estilo de Kotlin recomiendan nombrar los parámetros booleanos explícitamente (`enable = true`) para mejorar la legibilidad del código.
- **Corrección**: Se añadió el nombre del parámetro.
  ```kotlin
  // Antes
  crossfade(true)
  
  // Después
  crossfade(enable = true)
  ```

---

## 3. Endpoint de API no utilizado (`getGameById`)
- **Archivo afectado**: [FreeToGameApi.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/data/FreeToGameApi.kt)
- **Advertencia**: *Function "getGameById" is never used.*
- **¿Por qué ocurría?**: El método fue declarado en la interfaz para propositos educativos y futuros detalles de juegos, pero aún no se invocaba en la `MainActivity`.
- **Corrección**: Se añadió la anotación `@Suppress("unused")` para mantener el método disponible con fines educativos sin generar ruido en el linter.
  ```kotlin
  @Suppress("unused")
  @GET("game")
  suspend fun getGameById(@Query("id") id: Int): Game
  ```

---

## 4. Falta de coma final (*trailing comma*) en `Game.kt`
- **Archivo afectado**: [Game.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/data/Game.kt)
- **Advertencia**: *Missing trailing comma at the end of the line.*
- **¿Por qué ocurría?**: Las buenas prácticas de Kotlin recomiendan usar comas finales en listas multilínea para que los futuros cambios de código generen diferencias (`diffs`) en Git más limpias.
- **Corrección**: Se añadió la coma final:
  ```kotlin
  data class Game(
      @SerializedName("id") val id: Int,
      @SerializedName("title") val title: String,
      @SerializedName("thumbnail") val thumbnail: String,
      @SerializedName("short_description") val shortDescription: String, // <- Coma final añadida
  )
  ```

---

## 5. Declaración de espacio de nombres no utilizada en XML (`activity_main.xml`)
- **Archivo afectado**: [activity_main.xml](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/res/layout/activity_main.xml)
- **Advertencia**: *Namespace declaration is never used (xmlns:app).*
- **¿Por qué ocurría?**: El contenedor raíz `ConstraintLayout` declaraba el espacio de nombres `xmlns:app`, pero ninguna vista interna lo utilizaba ya que el `RecyclerView` abarca todo el ancho y alto (`match_parent`).
- **Corrección**: Se removió el atributo `xmlns:app` sobrante para optimizar el recurso XML.
