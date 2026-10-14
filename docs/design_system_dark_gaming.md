# Documentación Fase 1: Sistema de Diseño "Dark Gaming / Cyberpunk Minimal"

Antes de aplicar los cambios en el código de la aplicación, este documento explica el **por qué** y el **para qué** de la nueva identidad visual basada en el PRD de FreeToGame Mobile Client.

---

## 1. ¿Por qué un Tema "Dark Gaming"?
- **Inmersión y Experiencia Gamer**: Los usuarios de videojuegos están habituados a interfaces oscuras (*dark mode*). Un fondo oscuro reduce la fatiga visual en sesiones prolongadas y hace que las portadas a color de los videojuegos (`thumbnails`) resalten mucho más.
- **Estética Cyberpunk Minimal**: Transmite modernidad, velocidad y tecnología, alineándose con el propósito de descubrir títulos de PC y Web.

---

## 2. Definición y Propósito de los Tokens de Color

| Token / Color | Código Hex | ¿Por qué se usa? | ¿Para qué sirve? |
| :--- | :--- | :--- | :--- |
| **Dark Navy (Fondo)** | `#0f131c` | Es un tono negro azulado muy profundo. | Evita el negro puro (#000000) que puede generar deslumbramiento OLED, proporcionando un fondo inmersivo y elegante. |
| **Deep Indigo (Tarjetas)** | `#181c24` | Un tono azul marino ligeramente más claro que el fondo. | Permite diferenciar visualmente las tarjetas de juegos, menús y contenedores flotantes creando profundidad (*Material Depth*). |
| **Electric Cyan (Acento)** | `#00e5ff` | Un cian brillante de alta visibilidad. | Funciona como color de acento principal para CTAs (botones de acción), estados activos en menús y badges destacados. |
| **Text Light (Principal)** | `#F8FAFC` | Blanco grisáceo de altísimo contraste. | Garantiza una lectura impecable para títulos y textos principales cumpliendo normativas de accesibilidad WCAG AA. |
| **Text Muted (Secundario)** | `#94A3B8` | Gris azulado medio. | Ideal para subtítulos, metadatos y descripciones cortas sin restar protagonismo al contenido principal. |

---

## 3. Traducción a Componentes Visuales

1. **Barra Superior (Toolbar) y Navegación Inferior (BottomNavigationView)**:
   - Utilizarán los tonos `Dark Navy` e `Indigo Profundo` con acentos en `Electric Cyan` para mantener una cohesión visual total en el *App Shell*.
2. **Tarjetas de Videojuegos (`item_game.xml`)**:
   - Fondo de tarjeta en `Deep Indigo` (`#181c24`), bordes sutiles redondeados y tipografía de alto contraste.
