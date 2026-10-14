# Especificación de Animaciones y Transiciones de Pantalla (Android) - FreeToGame Mobile

---

## 1. Filosofía de Movimiento y Principios Rectores (Motion Design)

Para **FreeToGame Mobile**, el diseño de movimiento combina la respuesta táctil instantánea propia de los juegos competitivos con la fluidez moderna de **Material 3 / Predictive Back** en Android.

* **Agilidad y Precisión:** Las transiciones deben sentirse rápidas (250ms - 350ms) sin caídas de frame (target: 60fps / 120Hz ProMotion).
* **Continuidad Espacial:** Los elementos visuales compartidos (como la tarjeta del juego convirtiéndose en la cabecera de detalle) anclan la atención del usuario evitando saltos bruscos.
* **Curvas de Aceleración Físicas:** Uso estricto de curvas cúbicas no lineales (`EmphasizedEasing` y `DecelerateEasing`).
* **Respeto a la Accesibilidad:** Soporte automático para la opción de accesibilidad del sistema `Settings.Global.TRANSITION_ANIMATION_SCALE` (Reducir movimiento).

---

## 2. Tokenización de Curvas y Duraciones (Design Tokens)

### 2.1. Tokens de Duración (Duration Tokens)
| Token Name | Duración | Uso Principal |
| :--- | :--- | :--- |
| `motion-duration-short` | **150ms** | Microinteracciones, feedback de taps, toggles |
| `motion-duration-medium` | **250ms** | Cambio entre pestañas de navegación (Bottom Nav) |
| `motion-duration-long` | **350ms** | Transición jerárquica (Push/Pop Explorar ➔ Detalle) |
| `motion-duration-extended` | **450ms** | Diálogos modales y hojas inferiores (*Bottom Sheets*) |

### 2.2. Tokens de Curvas de Aceleración (Easing Tokens)
* **Emphasized Easing (Entrada con deceleración natural):**
  - Valor Cúbico: `CubicBezier(0.05, 0.7, 0.1, 1.0)`
  - Equivalente Jetpack Compose: `FastOutSlowInEasing` / `EmphasizedEasing`
  - Uso: Pantallas entrantes, cards en expansión.
* **Decelerate Easing:**
  - Valor Cúbico: `CubicBezier(0.0, 0.0, 0.2, 1.0)`
  - Equivalente Android XML: `@android:interpolator/decelerate_cubic`
* **Accelerate Easing (Salida de pantalla):**
  - Valor Cúbico: `CubicBezier(0.3, 0.0, 0.8, 0.15)`
  - Uso: Elementos que abandonan la pantalla o repliegue de modales.

---

## 3. Especificación de Transiciones por Flujo de Pantalla

### 3.1. Flujo 1: Explorar ➔ Detalle del Videojuego (Master-Detail Shared Element)
* **Tipo:** **Container Transform / Shared Element Transition**
* **Comportamiento:**
  - **Elemento compartido (Shared Element):** La portada/card del juego en el feed (`hero_thumbnail_card`) se transforma suavemente en la cabecera multimedia panorámica de la pantalla de detalle (`hero_banner_header`).
  - **Pantalla Entrante (Detalle):**
    - Escala: `0.92f` ➔ `1.0f`
    - Opacidad: `0f` ➔ `1f` (fundido de entrada en los primeros 120ms)
    - Desplazamiento Y: `+48dp` ➔ `0dp`
    - Duración: **350ms** (`EmphasizedDecelerateEasing`)
  - **Pantalla Saliente (Explorar):**
    - Escala: `1.0f` ➔ `0.96f`
    - Opacidad: `1.0f` ➔ `0.4f` (efecto profundidad oscura con `#0a0e16`)
    - Duración: **250ms**
  - **Retorno (Pop / Predictive Back Gesture):**
    - Inversión simétrica guiada por el gesto del usuario (*Predictive Back* Android 14+), colapsando el banner de vuelta al ítem correspondiente del RecyclerView / LazyColumn.

---

### 3.2. Flujo 2: Navegación entre Pestañas Inferiores (Bottom Nav Bar Switches)
* **Rutas:** `Explorar` ⇄ `Categorías` ⇄ `Regalos` ⇄ `Guardados`
* **Tipo:** **Fade Through Horizontal / Axis Transition**
* **Comportamiento:**
  - Si el usuario se desplaza hacia una pestaña a la derecha (ej. Explorar ➔ Regalos):
    - **Página Saliente:** Slide Out hacia la izquierda (`-32dp`), Fade Out de `1.0f` a `0f` (duración: **120ms**).
    - **Página Entrante:** Slide In desde la derecha (`+32dp` ➔ `0dp`), Fade In de `0f` a `1.0f` (duración: **220ms**, delay: **60ms**).
  - Si el usuario se desplaza a la izquierda, la dirección del eje X se invierte.
  - La barra inferior (`BottomNavigationBar`) y el encabezado superior permanecen estables y fijos para evitar parpadeos.

---

### 3.3. Flujo 3: Modal de Filtros Avanzados / Tags (Bottom Sheet Dialog)
* **Tipo:** **Slide Up Bottom Sheet**
* **Entrada:**
  - Desplazamiento Y: `100%` ➔ `0%`
  - Scrim de Fondo: Fade de `#00000000` a `#A60A0E16` (65% opacidad)
  - Duración: **300ms** (`EmphasizedDecelerateEasing`)
* **Salida:**
  - Desplazamiento Y: `0%` ➔ `100%`
  - Duración: **200ms** (`AccelerateEasing`)

---

## 4. Implementación en Android: Jetpack Compose (Código Listo para Producción)

```kotlin
// =========================================================================
// FreeToGameMotionTokens.kt
// =========================================================================
package com.freetogame.mobile.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween

object FreeToGameMotion {
    const val DURATION_SHORT = 150
    const val DURATION_MEDIUM = 250
    const val DURATION_LONG = 350

    val EmphasizedEasing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
    val DecelerateEasing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
    val AccelerateEasing = CubicBezierEasing(0.3f, 0.0f, 0.8f, 0.15f)
}

// =========================================================================
// NavGraphTransitions.kt (NavHost con Compose Navigation y Shared Elements)
// =========================================================================
package com.freetogame.mobile.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.freetogame.mobile.ui.theme.FreeToGameMotion

fun NavGraphBuilder.freeToGameNavGraph() {
    // Transición de cambio entre Tabs (Fade Through con sutil Slide)
    composable(
        route = "explore",
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_MEDIUM,
                    easing = FreeToGameMotion.DecelerateEasing
                )
            ) + slideInHorizontally(
                initialOffsetX = { -80 },
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_MEDIUM,
                    easing = FreeToGameMotion.DecelerateEasing
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_SHORT,
                    easing = FreeToGameMotion.AccelerateEasing
                )
            ) + slideOutHorizontally(
                targetOffsetX = { -80 },
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_SHORT
                )
            )
        }
    ) {
        ExploreScreen()
    }

    // Transición Jerárquica hacia Detalle del Juego (Elevación + Push)
    composable(
        route = "game_detail/{gameId}",
        enterTransition = {
            fadeIn(
                animationSpec = tween(150, easing = FreeToGameMotion.DecelerateEasing)
            ) + slideInVertically(
                initialOffsetY = { fullHeight -> (fullHeight * 0.15f).toInt() },
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_LONG,
                    easing = FreeToGameMotion.EmphasizedEasing
                )
            ) + scaleIn(
                initialScale = 0.94f,
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_LONG,
                    easing = FreeToGameMotion.EmphasizedEasing
                )
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(150, easing = FreeToGameMotion.AccelerateEasing)
            ) + slideOutVertically(
                targetOffsetY = { fullHeight -> (fullHeight * 0.15f).toInt() },
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_MEDIUM,
                    easing = FreeToGameMotion.AccelerateEasing
                )
            ) + scaleOut(
                targetScale = 0.94f,
                animationSpec = tween(
                    durationMillis = FreeToGameMotion.DURATION_MEDIUM
                )
            )
        }
    ) { backStackEntry ->
        val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
        GameDetailScreen(gameId = gameId)
    }
}
```

---

## 5. Implementación Clásica en Android (XML Navigation / View System)

### 5.1. `res/anim/slide_in_detail.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:interpolator/fast_out_slow_in">
    <translate
        android:fromYDelta="15%p"
        android:toYDelta="0"
        android:duration="350" />
    <alpha
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
        android:duration="200" />
    <scale
        android:fromXScale="0.94"
        android:toXScale="1.0"
        android:fromYScale="0.94"
        android:toYScale="1.0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:duration="350" />
</set>
```

### 5.2. `res/anim/slide_out_detail_pop.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:interpolator/fast_out_linear_in">
    <translate
        android:fromYDelta="0"
        android:toYDelta="15%p"
        android:duration="250" />
    <alpha
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:duration="180" />
    <scale
        android:fromXScale="1.0"
        android:toXScale="0.95"
        android:fromYScale="1.0"
        android:toYScale="0.95"
        android:pivotX="50%"
        android:pivotY="50%"
        android:duration="250" />
</set>
```

---

## 6. Lista de Verificación de Calidad (Motion QA Checklist)

1. **Test a 120Hz / 60Hz:** Comprobar con la herramienta *Systrace / Perfetto* en Android Studio que ninguna transición provoque *jank* o caídas de frame.
2. **Predictive Back (Android 14+):** Asegurar que al deslizar desde el borde izquierdo en la pantalla de detalle, la pantalla responda interactivamente al gesto del dedo antes de confirmarse la salida.
3. **Persistencia del Sticky Bottom CTA:** El botón *"Obtener Juego"* debe entrar con un retardo de `+100ms` tras la carga del contenido o fijarse mediante un contenedor estático para evitar rebotes visuales.
4. **Respeto a `ANIMATOR_DURATION_SCALE`:** Si el usuario desactiva animaciones en las opciones de desarrollador o accesibilidad, el sistema debe resolver las pantallas de forma instantánea.
