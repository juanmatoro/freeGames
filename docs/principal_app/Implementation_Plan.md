# Plan de Implementación: Pantalla Principal "Explorar" (Basado en `DESIGN.md` y `screen.png`)

Este documento detalla el **por qué** y el **para qué** de cada componente que añadiremos a la pantalla principal para replicar fielmente el diseño de alta fidelidad.

---

## 1. Componentes a Implementar

### A. Barra de Cabecera y Buscador en Vivo (`activity_main.xml`)
- **¿Por qué?**: El diseño requiere acceso inmediato a la búsqueda por texto y a los selectores de plataforma (Todo | PC | Web) sin recargar la vista.
- **¿Para qué sirve?**: 
  - Permite al usuario filtrar juegos escribiendo su nombre.
  - Ofrece pestañas de plataforma interactivas para alternar instantáneamente entre títulos de Windows y Navegador Web.

### B. Tarjeta de Destacado de Hoy (*Hero Card*)
- **¿Por qué?**: Los juegos destacados generan mayor impacto visual (*engagement*) al abrir la aplicación.
- **¿Para qué sirve?**: Muestra el juego estrella del día con una portada grande (16:9), subtítulos de género, sinopsis y un botón de acción rápida *"Ver Detalles"*.

### C. Carrusel de Géneros (*Chips*)
- **¿Por qué?**: Facilita la exploración temática por géneros populares de forma táctil y horizontal (*thumb-friendly*).
- **¿Para qué sirve?**: Al tocar cualquier género (MMORPG, Shooter, MOBA), se filtran automáticamente los juegos del feed principal.

### D. Refinamiento de Tarjetas del Feed ("Populares Gratis")
- **¿Por qué?**: Las tarjetas deben mostrar claramente la etiqueta "GRATIS", miniaturas limpias, género y un botón de guardado (*bookmark*).
- **¿Para qué sirve?**: Permite escanear rápidamente la lista de juegos populares con una estética minimalista Cyberpunk.
