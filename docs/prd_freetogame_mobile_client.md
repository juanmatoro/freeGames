# PRD: FreeToGame Mobile Client

---

## 1. Resumen Ejecutivo y Visión del Producto
**FreeToGame Mobile** es una aplicación cliente nativa / web-app optimizada para dispositivos móviles diseñada para descubrir, explorar y gestionar el acceso a videojuegos gratuitos (Free-to-Play) en múltiples plataformas (PC Windows y Navegador Web). 

La aplicación consume directamente la API pública de **[FreeToGame](https://www.freetogame.com/api-doc)**, proporcionando una interfaz moderna, inmersiva y de alto rendimiento orientada a la comunidad gamer, con estética dark-mode futurista y soporte de filtrado granular.

---

## 2. Objetivos del Negocio y del Producto
1. **Descubrimiento Ágil:** Reducir la fricción para que los usuarios encuentren títulos gratuitos de alta calidad adaptados a sus gustos y plataformas.
2. **Fidelidad Técnica con la API:** Mapear al 100% las capacidades de los endpoints de FreeToGame (`/games`, `/game?id=...`, `/filter`).
3. **Engagement & Retención:** Ofrecer herramientas para guardar favoritos, seguir actualizaciones y alertar sobre promociones/regalos activos (*Giveaways*).
4. **Experiencia de Usuario Premium:** Aplicar estándares de diseño móvil modernos (Atomic Design, microinteracciones, tipografía *Plus Jakarta Sans*, accesibilidad WCAG AA en tema oscuro).

---

## 3. Público Objetivo (User Personas)
* **Gamer Casual:** Busca juegos sin coste para jugar en navegador o partidas rápidas en portátil sin pagar suscripciones.
* **Jugador Competitivo:** Busca títulos F2P tácticos (shooters, MOBAs, battle royales) para PC con requisitos técnicos bien especificados.
* **Cazador de Ofertas y Botines:** Interesado en promociones exclusivas, claves gratuitas y lanzamientos recientes sin coste.

---

## 4. Arquitectura de Navegación y Vistas (App Shell)

La interfaz se estructura mediante un **App Shell móvil** consistente:
- **Top App Bar (Header Global):**
  - Isotipo / Logotipo con acento neón `#00e5ff`.
  - Acceso a notificaciones de eventos / regalos.
  - Perfil de usuario y configuración del cliente.
  - Selector global de plataforma: `Todo` | `PC` | `Web`.
- **Bottom Navigation Bar (Pestañas Fijas):**
  1. 🎮 **Explorar:** Catálogo principal, destacados del día y búsqueda.
  2. 🗂️ **Categorías:** Explorador temático por géneros, tags y mecánicas.
  3. 🎁 **Regalos (Giveaways):** Monitor de promociones y botín gratuito.
  4. 🔖 **Guardados:** Lista de deseos personal y biblioteca de seguimiento.

---

## 5. Especificación de Pantallas y Funcionalidades

### 5.1. Pantalla 1: Explorar (Feed Principal y Descubrimiento)
* **Buscador en Vivo:** Búsqueda textual por nombre (`?title=...`).
* **Selector de Plataforma:**
  - `Todos` (all)
  - `PC (Windows)` (`platform=pc`)
  - `Navegador Web` (`platform=browser`)
* **Filtros por Géneros Principales:** Carrusel horizontal con chips (MMORPG, Shooter, MOBA, Battle Royale, RPG, etc.).
* **Filtros por Etiquetas / Tags Combinados:** Selección múltiple (`tag=pvp.open-world.sci-fi`).
* **Criterio de Ordenación (`sort-by`):** Relevancia, Popularidad, Fecha de lanzamiento, Alfabético.
* **Sección "Destacado de Hoy":** Hero card con badges de género, estado de gratuidad y CTA a la vista de detalle.
* **Feed de Juegos:** Tarjetas con portada optimizada, título, descripción corta, género, icono de plataforma y botón rápido de guardar.
* **Paginación / Carga Infinita:** Botón o infinite scroll de títulos verificados (420+ títulos en base de datos).

### 5.2. Pantalla 2: Detalle del Videojuego (`/api/game?id=...`)
* **Hero Multimedia:** Imagen de cabecera con efecto viñeta, botón flotante de retorno, compartir y añadir a favoritos.
* **Cabecera de Título:**
  - Thumbnail oficial del juego.
  - Título, estudio desarrollador y publisher.
  - Valoración media e indicador en vivo (*Live Servers*).
* **Acciones Principales:**
  - Botón primario: *"Jugar Ahora"* / *"Obtener Juego"* (enlace a `freetogame_profile_url` o descarga directa).
  - Botón secundario: *"Favorito"* (persistencia local o en cuenta).
* **Ficha Técnica Modular (Key Specs):**
  - Plataforma (`platform`)
  - Género (`genre`)
  - Fecha de lanzamiento (`release_date`)
  - Desarrollador / Estudio (`developer` / `publisher`)
* **Acerca del Juego:** Sinopsis extendida (`description`) con opción expansible *"Leer más"*.
* **Galería de Capturas de Pantalla:** Carrusel táctil con capturas de pantalla de la API en alta resolución (`screenshots`).
* **Requisitos Mínimos del Sistema (`minimum_system_requirements`):**
  - Desglose por OS, Procesador (CPU), Gráficos (GPU), RAM y Almacenamiento.
  - Mensaje alternativo amigable para juegos de navegador.
* **Juegos Similares:** Carrusel de títulos relacionados por género/etiquetas.
* **Sticky Bottom CTA:** Barra fija persistente con botón *"Obtener Juego / Descarga Oficial"* con enlace externo seguro.

### 5.3. Pantallas Planificadas (Roadmap Fase 2)
* **Categorías:** Matriz interactiva de géneros con conteo de juegos por categoría.
* **Regalos & Giveaways:** Vista con temporizadores para drops y códigos promocionales activos.
* **Guardados & Perfil:** Biblioteca del usuario con filtrado offline y notas personales.

---

## 6. Integración con la API de FreeToGame

| Endpoint | Método | Parámetros Clave | Uso en la App |
| :--- | :--- | :--- | :--- |
| `/api/games` | GET | `platform`, `category`, `sort-by` | Feed general y listas filtradas |
| `/api/game` | GET | `id` | Vista de detalle de un juego específico |
| `/api/filter` | GET | `tag`, `platform` | Búsqueda combinada con múltiples etiquetas |
| `/api/giveaways` | GET | `platform` | Pestaña de regalos y promociones |

---

## 7. Sistema de Diseño (Design System Tokens)

* **Tema:** Dark Gaming / Cyberpunk Minimal.
* **Color Primario (Acento):** Cyan Eléctrico (`#00e5ff`) para CTAs principales, badges y estados activos.
* **Color Secundario:** Azul / Índigo Profundo (`#181c24` a `#222834`) para contenedores y tarjetas.
* **Fondo Superficie:** Dark Navy / Carbon (`#0f131c` / `#0a0e16`).
* **Tipografía:** *Plus Jakarta Sans* (pesos 400, 500, 600, 700).
* **Radio de Bordes:** 8px a 16px (bordes redondeados modernos y consistentes).

---

## 8. Criterios de Aceptación y Calidad (QA)
1. **Responsive Mobile First:** Vistas diseñadas para viewport estándar de 390px - 430px de ancho con navegación a una mano (*thumb-friendly zone*).
2. **Resiliencia ante Datos Nulos:** Si un juego no tiene `minimum_system_requirements` (típico en juegos web), la UI debe renderizar una nota de compatibilidad sin romper el layout.
3. **Rendimiento:** Tiempos de carga percibidos rápidos mediante esqueletos de carga (*skeletons*) y optimización de imágenes remotas.
