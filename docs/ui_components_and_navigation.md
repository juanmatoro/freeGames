# Guía Educativa: Creación de Componentes de Vista y Navegación Detalle (List & Single)

Este documento explica paso a paso cómo hemos construido los componentes visuales de la aplicación: desde la **lista principal de juegos (`RecyclerView`)** hasta la **vista de detalle individual (*Single/Detail*)**, y cómo se enlazan ambas pantallas para navegar entre ellas.

---

## Índice
1. [Paso 1: Diseño de la Lista y las Tarjetas Individuales](#1-paso-1-diseño-de-la-lista-y-las-tarjetas-individuales)
2. [Paso 2: Creación del Adaptador (`GameAdapter`) y Clics con Lambdas](#2-paso-2-creación-del-adaptador-gameadapter-y-clics-con-lambdas)
3. [Paso 3: Creación de la Vista de Detalle (*Single Game*)](#3-paso-3-creación-de-la-vista-de-detalle-single-game)
4. [Paso 4: Enlace entre la Lista y el Detalle (`MainActivity` e `Intent`)](#4-paso-4-enlace-entre-la-lista-y-el-detalle-mainactivity-e-intent)
5. [Paso 5: Declaración de la Actividad en el Manifiesto](#5-paso-5-declaración-de-la-actividad-en-el-manifiesto)

---

## 1. Paso 1: Diseño de la Lista y las Tarjetas Individuales

### A. El contenedor de la lista (`activity_main.xml`)
En la pantalla principal, colocamos un `RecyclerView` que ocupará todo el espacio para listar los elementos de forma eficiente:
```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
- **¿Por qué?**: El `RecyclerView` es el componente optimizado de Android para mostrar listas grandes. Recicla las celdas que salen de la pantalla para ahorrar memoria.

### B. El diseño de cada tarjeta individual (`item_game.xml`)
Creamos un diseño XML independiente para representar cada juego en la lista [item_game.xml](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/res/layout/item_game.xml):
- Una miniatura de imagen (`ImageView`).
- Un título de texto (`TextView`).
- Una descripción corta (`TextView`).

---

## 2. Paso 2: Creación del Adaptador (`GameAdapter`) y Clics con Lambdas

El `GameAdapter` actúa como puente entre la lista de datos (`List<Game>`) y las vistas XML individuales.

En [GameAdapter.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/adapters/GameAdapter.kt), añadimos un parámetro `onItemClick: (Game) -> Unit`:

```kotlin
class GameAdapter(
    private var games: List<Game>,
    private val onItemClick: (Game) -> Unit // <- Callback para cuando el usuario toca un juego
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

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

        // Detectamos el clic en toda la tarjeta de la celda
        holder.itemView.setOnClickListener {
            onItemClick(game)
        }
    }

    override fun getItemCount(): Int = games.size

    fun updateData(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}
```

### ¿Por qué usamos una Lambda (`onItemClick`)?
Permite separar responsabilidades. El Adaptador no necesita saber qué pasa al hacer clic; simplemente avisa a la `MainActivity` pasándole el objeto `Game` exacto que fue presionado.

---

## 3. Paso 3: Creación de la Vista de Detalle (*Single Game*)

Para mostrar la información completa de un juego seleccionado, creamos una nueva pantalla:
1. **Layout XML** ([activity_game_detail.xml](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/res/layout/activity_game_detail.xml)): Contiene una imagen grande en la cabecera, un título en grande y la descripción detallada.
2. **Activity** ([GameDetailActivity.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/activities/GameDetailActivity.kt)): Recoge los datos enviados desde la lista a través del `Intent` y los pinta usando ViewBinding y Coil.

```kotlin
class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recogemos los datos enviados por el Intent
        val title = intent.getStringExtra("EXTRA_TITLE") ?: ""
        val description = intent.getStringExtra("EXTRA_DESC") ?: ""
        val thumbnail = intent.getStringExtra("EXTRA_THUMBNAIL") ?: ""

        // Pintamos los componentes
        binding.tvDetailTitle.text = title
        binding.tvDetailDescription.text = description
        binding.ivDetailThumbnail.load(thumbnail) {
            crossfade(enable = true)
        }
    }
}
```

---

## 4. Paso 4: Enlace entre la Lista y el Detalle (`MainActivity` e `Intent`)

En [MainActivity.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/activities/MainActivity.kt), al inicializar el adaptador, definimos qué hacer cuando el usuario hace clic en un juego:

```kotlin
private fun setupRecyclerView() {
    gameAdapter = GameAdapter(emptyList()) { game ->
        // Creamos un Intent para navegar de MainActivity a GameDetailActivity
        val intent = Intent(this, GameDetailActivity::class.java).apply {
            putExtra("EXTRA_TITLE", game.title)
            putExtra("EXTRA_DESC", game.shortDescription)
            putExtra("EXTRA_THUMBNAIL", game.thumbnail)
        }
        startActivity(intent)
    }
    
    binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = gameAdapter
    }
}
```

### ¿Por qué y para qué usamos un `Intent`?
El `Intent` es el mecanismo nativo de Android para mensajería entre componentes. Con `putExtra(...)`, empaquetamos los datos del juego seleccionado y con `startActivity(intent)` abrimos la pantalla de detalle.

---

## 5. Paso 5: Declaración de la Actividad en el Manifiesto

Toda nueva Activity creada en Android debe registrarse obligatoriamente en el archivo [AndroidManifest.xml](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/AndroidManifest.xml):

```xml
<activity
    android:name=".activities.GameDetailActivity"
    android:exported="false" />
```
- **`exported="false"`**: Indica que esta pantalla de detalle solo puede ser abierta desde dentro de nuestra propia aplicación (por nuestra `MainActivity`).
