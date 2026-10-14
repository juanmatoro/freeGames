# Documentación de Componentes: 1. Tokens de Color y 2. Tarjeta de Videojuego (`item_game.xml`)

Este documento detalla el **por qué** y el **para qué** de la implementación de los dos primeros componentes visuales basados en `Browser_DESIGN.md`.

---

## Componente 1: Refinamiento de Tokens de Color (Dark Gaming Palette)

### ¿Por qué lo hacemos?
El diseño Cyberpunk Minimal requiere una calibración cromática muy específica para evitar fatiga visual y lograr que las imágenes de los videojuegos destaquen con alto contraste. Los colores por defecto de Android no se adaptan a esta identidad.

### ¿Para qué sirve cada token?
- **`canvas_base` (`#0F131C`)**: Fondo general de la aplicación. Proporciona una superficie profunda e inmersiva que simula un lanzador de juegos de PC.
- **`surface_card` (`#181C24`)**: Color de fondo para las tarjetas de juego (`MaterialCardView`). Crea elevación visual y separación respecto al fondo general sin necesidad de sombras pesadas.
- **`electric_cyan` (`#00E5FF`)**: Color de acento principal. Se utiliza para elementos activos, bordes de enfoque y llamadas a la acción (CTA).
- **`text_high_emphasis` (`#F8FAFC`)**: Texto blanco-plateado para títulos y nombres de juegos, garantizando lectura instantánea.
- **`text_muted` (`#94A3B8`)**: Gris pizarra para subtítulos y metadatos (descripciones cortas, géneros).

---

## Componente 2: Tarjeta de Videojuego (`item_game.xml`)

### ¿Por qué lo hacemos?
La tarjeta es el elemento central de la pantalla de exploración. Siguiendo el sistema de diseño, debe equilibrar una estética moderna (bordes redondeados, bordes sutiles tipo *stroke*) con una distribución *thumb-friendly* (imagen a la izquierda, textos a la derecha).

### ¿Para qué sirve su estructura?
1. **`MaterialCardView` con esquinas de 12px/16px y borde sutil (`strokeColor="#222834"`)**: 
   - *Por qué*: Define una separación limpia y moderna entre tarjetas sin depender de sombras oscuras tradicionales.
2. **`ImageView` (`ivThumbnail`) con 120x85dp y `centerCrop`**: 
   - *Por qué*: Mantiene una proporción estándar para las portadas de juegos, evitando distorsiones visuales.
3. **`TextView` para Título y Descripción**:
   - *Por qué*: El título utiliza `text_high_emphasis` con peso en negrita, mientras que la descripción corta usa `text_muted` con un límite de 2 líneas (`maxLines="2"`, `ellipsize="end"`) para mantener un diseño compacto y ordenado.
