# Documentación Maestra: FreeToGame Mobile Client (Android Kotlin)

Este documento constituye la referencia técnica y educativa completa de la aplicación **FreeToGame Mobile**, desarrollada bajo una arquitectura moderna, componentes nativos y el sistema de diseño **Cyberpunk Dark Gaming / Minimal**.

---

## 1. Visión General y Arquitectura
FreeToGame Mobile es una aplicación cliente optimizada para dispositivos móviles que conecta directamente con la API pública de FreeToGame para descubrir videojuegos gratuitos (*Free-to-Play*).

### Estructura de Paquetes
```text
com.example.freegames/
├── activities/       # Controladores de pantalla (MainActivity, GameDetailActivity)
├── adapters/         # Adaptador del RecyclerView (GameAdapter)
└── data/             # Modelos de datos (Game, GameDetail) y cliente Retrofit (FreeToGameApi)
```

---

## 2. Tecnologías Clave y Buenas Prácticas

### A. Red y Sincronización con Retrofit 2
- **¿Qué hace?**: Convierte endpoints HTTP en interfaces tipadas de Kotlin.
- **Implementación**: [FreeToGameApi.kt](file:///Users/Mananas/Develops/FreeGames-Android/app/src/main/java/com/example/freegames/data/FreeToGameApi.kt) utiliza funciones `suspend` integradas con **Kotlin Coroutines** (`Dispatchers.IO`) para realizar peticiones de red en segundo plano sin bloquear la interfaz.
- **Endpoints soportados**: `/games` (con filtros opcionales de categoría y plataforma) y `/game?id=...` (para la ficha técnica detallada).

### B. Carga Eficiente de Imágenes con Coil
- **¿Qué hace?**: Descarga, cachea y renderiza imágenes de forma asíncrona sin descargar archivos manualmente.
- **Implementación**: `imageView.load(url) { crossfade(enable = true) }` en adaptadores y actividades, gestionando caché en memoria y disco automáticamente.

### C. Seguridad con ViewBinding
- **¿Qué hace?**: Reemplaza el obsoleto `findViewById` generando clases de enlace (*Binding*) en tiempo de compilación.
- **Gestión de IDs**: Los IDs definidos en el XML (`android:id="@+id/..."`) se mapean automáticamente a nombres en formato *CamelCase* en las clases `ActivityMainBinding`, `ItemGameBinding` y `ActivityGameDetailBinding`.

---

## 3. Sistema de Diseño: Cyberpunk Dark Gaming (`Browser_DESIGN.md`)

Inspirado en launchers modernos de PC, la interfaz prioriza el contenido multimedia mediante un modo oscuro inmersivo:
- **Base Canvas (`#0F131C`)**: Fondo oscuro principal (`canvas_base`).
- **Superficie de Tarjetas (`#181C24`)**: Contenedores y barras (`surface_card`).
- **Acento Electric Cyan (`#00E5FF`)**: Color primario para estados activos, botones CTA y badges de descarga.
- **Tipografía de Alto Contraste**: Títulos en blanco plateado (`#F8FAFC`) y metadatos en gris pizarra (`#94A3B8`).

---

## 4. Componentes Principales y Funcionalidades

### A. Pantalla Principal ("Explorar")
- **Header Personalizado**: Isotipo de marca con insignia *"FREETOGAME"* y accesos a notificaciones y perfil.
- **Buscador en Vivo (*Live Search*)**: Filtrado local en tiempo real a medida que el usuario escribe en el campo de búsqueda (`etSearch`).
- **Selector de Plataforma y Géneros**: Pestañas de plataforma (*TODO, PC, WEB*) y carrusel horizontal de chips de géneros principales.
- **Scroll Infinito (*Infinite Scroll*)**: Paginación progresiva por lotes de 20 elementos mediante un `OnScrollListener` en el `RecyclerView`.

### B. Pantalla de Detalle (`GameDetailActivity`)
- **Ficha Técnica Modular**: Despliega desarrollador, editor, fecha de lanzamiento y plataforma.
- **Requisitos del Sistema con Resiliencia ante Nulos**: Comprueba si el objeto `minimum_system_requirements` está presente; si el juego es de navegador web y carece de requisitos, muestra un mensaje amigable.
- **Sticky Bottom CTA**: Botón flotante inferior fijo en *Electric Cyan* que abre el enlace oficial de descarga del juego (`freetogame_profile_url`) en el navegador externo.

---

## 5. Navegación (App Shell)
- **Bottom Navigation Bar**: Barra inferior flotante con 4 destinos principales:
  1. 🎮 **Explorar** (Catálogo principal y búsqueda).
  2. 🗂️ **Categorías** (Selector de géneros mediante diálogo).
  3. 🎁 **Regalos** (Monitor de give-aways).
  4. 🔖 **Guardados** (Biblioteca personal).
