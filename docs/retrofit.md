# Guía Educativa: Retrofit 2 en Android

**Retrofit** es la librería estándar de Square para Android que convierte servicios HTTP en interfaces de Kotlin. Nos permite comunicar nuestra aplicación con servidores externos (APIs REST) de forma limpia, segura y tipada.

---

## 1. ¿Por qué y para qué usamos Retrofit?

### El Problema Tradicional
Antiguamente, para conectarse a una API en Android, tenías que:
- Escribir código pesado con `HttpURLConnection` o `AsyncTask`.
- Gestionar manualmente los hilos de red y hilos de interfaz de usuario.
- Convertir manualmente las cadenas JSON crudas a objetos Java/Kotlin mediante `JSONObject` o `JSONArray`.

### La Solución con Retrofit
Retrofit automatiza todo el proceso repetitivo y propenso a errores:
- **Abstracción de Red**: Define endpoints HTTP simplemente usando anotaciones de Kotlin (`@GET`, `@POST`, etc.).
- **Conversión Automática**: Integrado con conversores como **Gson**, transforma automáticamente el JSON de la API en objetos de nuestras clases (`Data Classes`).
- **Integración con Corrutinas**: Soporta funciones `suspend` nativas de Kotlin para manejar código asíncrono sin bloquear la interfaz.

---

## 2. Componentes Clave y su Funcionamiento

En nuestro proyecto ([FreeToGameApi.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/data/FreeToGameApi.kt)), utilizamos los siguientes elementos:

### A. La Interfaz (`interface FreeToGameApi`)
Define los endpoints (rutas) de la API y qué datos esperamos recibir.

```kotlin
interface FreeToGameApi {

    @GET("games")
    suspend fun getGamesList(): List<Game>

    @GET("game")
    suspend fun getGameById(@Query("id") id: Int): Game
    
    ...
}
```

- **`@GET("games")`**: 
  - **¿Para qué sirve?**: Le indica a Retrofit que realice una petición HTTP `GET` al endpoint `https://www.freetogame.com/api/games`.
  - **¿Por qué se usa?**: Es la forma declarativa de pedir datos al servidor sin escribir código de conexión manual.
- **`suspend`**:
  - **¿Para qué sirve?**: Convierte la función en una corrutina de Kotlin.
  - **¿Por qué se usa?**: Permite pausar y reanudar la ejecución sin bloquear el hilo principal (evitando congelamientos o cierres por ANR).
- **`@Query("id")`**:
  - **¿Para qué sirve?**: Añade parámetros de consulta a la URL (ej: `?id=123`).

---

### B. El Cliente Singleton (`Retrofit.Builder`)
Configura cómo se va a conectar Retrofit y cómo procesará las respuestas.

```kotlin
companion object {
    fun getInstance(): FreeToGameApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(FreeToGameApi::class.java)
    }
}
```

- **`baseUrl(...)`**:
  - **¿Para qué sirve?**: Define la dirección raíz de la API. Todas las peticiones relativas (`@GET("games")`) se sumarán a esta URL base.
- **`addConverterFactory(GsonConverterFactory.create())`**:
  - **¿Para qué sirve?**: Añade el conversor de Gson.
  - **¿Por qué se usa?**: Sin esto, Retrofit solo devolvería texto plano (JSON). Gson traduce automáticamente el JSON a nuestras `data class` (`Game`).
- **`retrofit.create(...)`**:
  - **¿Para qué sirve?**: Genera dinámicamente una implementación de nuestra interfaz `FreeToGameApi` usando reflexión.

---

## 3. Uso Habitual en la Aplicación

Cuando queremos obtener los datos en una Actividad o ViewModel, combinamos Retrofit con Corrutinas:

```kotlin
CoroutineScope(Dispatchers.IO).launch {
    // 1. Petición en segundo plano (Hilo de Red/E S)
    val games = FreeToGameApi.getInstance().getGamesList()
    
    withContext(Dispatchers.Main) {
        // 2. Actualización de la UI en el Hilo Principal
        adapter.updateData(games)
    }
}
```
- **`Dispatchers.IO`**: Se usa para tareas de red o lectura de disco para no saturar el rendimiento de la pantalla.
- **`withContext(Dispatchers.Main)`**: Regresa al hilo principal obligatorio para pintar elementos en la interfaz (`RecyclerView`, `TextView`, etc.).
