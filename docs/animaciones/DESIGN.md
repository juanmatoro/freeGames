---
name: FreeToGame Mobile
colors:
  surface: '#0f131c'
  surface-dim: '#0f131c'
  surface-bright: '#353942'
  surface-container-lowest: '#0a0e16'
  surface-container-low: '#181c24'
  surface-container: '#1c2028'
  surface-container-high: '#262a33'
  surface-container-highest: '#31353e'
  on-surface: '#dfe2ee'
  on-surface-variant: '#bac9cc'
  inverse-surface: '#dfe2ee'
  inverse-on-surface: '#2c3039'
  outline: '#849396'
  outline-variant: '#3b494c'
  surface-tint: '#00daf3'
  primary: '#c3f5ff'
  on-primary: '#00363d'
  primary-container: '#00e5ff'
  on-primary-container: '#00626e'
  inverse-primary: '#006875'
  secondary: '#bdc2ff'
  on-secondary: '#131e8c'
  secondary-container: '#2f3aa3'
  on-secondary-container: '#a8afff'
  tertiary: '#ffe7e8'
  on-tertiary: '#67001b'
  tertiary-container: '#ffc1c4'
  on-tertiary-container: '#b40036'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#9cf0ff'
  primary-fixed-dim: '#00daf3'
  on-primary-fixed: '#001f24'
  on-primary-fixed-variant: '#004f58'
  secondary-fixed: '#e0e0ff'
  secondary-fixed-dim: '#bdc2ff'
  on-secondary-fixed: '#000767'
  on-secondary-fixed-variant: '#2f3aa3'
  tertiary-fixed: '#ffdadb'
  tertiary-fixed-dim: '#ffb2b7'
  on-tertiary-fixed: '#40000d'
  on-tertiary-fixed-variant: '#92002a'
  background: '#0f131c'
  on-background: '#dfe2ee'
  surface-variant: '#31353e'
typography:
  headline-xl:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '800'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 26px
    fontWeight: '700'
    lineHeight: 34px
    letterSpacing: -0.015em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '700'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 17px
    fontWeight: '600'
    lineHeight: 24px
  title-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 15px
    fontWeight: '600'
    lineHeight: 20px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  body-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 13px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.04em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 11px
    fontWeight: '600'
    lineHeight: 14px
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 10px
    fontWeight: '700'
    lineHeight: 12px
    letterSpacing: 0.06em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-mobile: 0.75rem
  margin: 1.25rem
  margin-mobile: 1rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 0.875rem
  space-lg: 1.25rem
  space-xl: 2rem
---

## Brand & Style

This design system establishes a high-performance, dark-mode-first environment tailored for free-to-play mobile gamers, collectors, and enthusiasts. Drawing inspiration from modern desktop launchers and game discovery clients, it projects technological precision, immersion, and speed.

The design language fuses modern dark UI architecture with tactical luminescence: deep, abyss-like backgrounds offset by calibrated electric blue and violet energy vectors. Surfaces feel atmospheric yet structural, prioritizing game cover art, reward badges, and real-time community engagement. Interface chrome stays subdued to spotlight vibrant gameplay imagery, using targeted neon halos and glass overlays strictly to indicate active states, claims, and telemetry.

## Colors

The palette relies on deep, non-pure black tones that eliminate eye strain while maximizing contrast against game artwork and interactive highlights.

### Surface Architecture
- **Base Canvas (`#0B0F17`)**: Deep obsidian base for view backgrounds and bottom navigation chrome.
- **Surface Layer 1 (`#131B2A`)**: Primary card containers, navigation rails, and bottom sheets.
- **Surface Layer 2 (`#1E293B`)**: Interactive wells, nested inputs, search fields, and hover/pressed states.
- **Surface Stroke (`#334155`)**: Ultra-subtle border treatment applied at low opacity (`rgba(51, 65, 85, 0.45)`) for edge definition without clutter.

### Accents & Signatures
- **Electric Cyan (`#00E5FF`)**: Primary action driver. Used for active navigation indicators, key CTA buttons ("Play Now", "Claim Key"), focused input boundaries, and progressive load states.
- **Neon Indigo (`#818CF8`)**: Secondary tactical accent. Used for platform category tags, seasonal battle-pass tiers, and secondary metrics.
- **Telemetry Coral (`#F43F5E`)**: Tertiary/Status accent. Dedicated to live status pings, limited-time drop countdowns, and critical alerts.

### Content Contrast
- **Text High-Emphasis (`#F8FAFC`)**: 100% white-slate for titles, primary stats, and game names.
- **Text Muted (`#94A3B8`)**: Mid-tone slate for studio metadata, genre tags, and system labels.
- **Text Inactive (`#475569`)**: Low-contrast slate for placeholder text and disabled glyphs.

## Typography

The type system balances technical crispness with expressive display weight:

- **Display & Headings (Plus Jakarta Sans)**: Delivers geometric clarity and structural authority. Weights 700 and 800 define high-impact section headers, hero game showcases, and stat tallies.
- **Body & Data Content (Inter)**: Handles long-form game descriptions, patch release logs, and user comments with neutral optical clarity across small screen viewports.
- **Pills, Badges & Micro-Chrome (Plus Jakarta Sans Upper/Semi-Bold)**: Uses positive tracking (`0.04em` to `0.06em`) for categorical pill tags, hardware specifications, and live timers to guarantee instant visual parsing at glance speeds.

## Layout & Spacing

Layouts conform to a compact, vertically fluid system designed around single-thumb reachable zones and horizontal snap-scroll rails.

### Responsive Breakpoints & Grids
- **Mobile Handset (<640px)**: 4-column layout; `margin-mobile` of 16px (1rem), column gutters of 12px (0.75rem). Hero banners snap full-width with a slight inner margin reveal.
- **Large Handset & Foldables (640px - 768px)**: 6-column layout; outer margin of 20px (1.25rem), gutters of 16px (1rem).
- **Tablet / Deck-Scale (>768px)**: 8-column layout; auto-centered view container maxing out at 1040px width with a fixed left-hand rail replacing the mobile bottom navigation bar.

### Tap Rhythm
Interactive touch targets maintain an absolute minimum height of 44px, scaling to 48px for primary action buttons. Vertical component rhythm follows an 8px base increment, with micro-spacing (pill tag internals, counter paddings) tuned to 4px.

## Elevation & Depth

This design system avoids high-contrast dropshadows, relying instead on surface luminance hierarchy, translucent glass plates, and directional neon underglows.

- **Level 0 (Floor)**: Canvas background `#0B0F17`.
- **Level 1 (Card & Content Well)**: Background `#131B2A` bordered with a 1px inner hairline of `rgba(255, 255, 255, 0.05)`.
- **Level 2 (Active/Floating Panels & Bottom Bars)**: `#1E293B` or frosted `#0B0F17` at 85% opacity with `backdrop-filter: blur(16px)` and a top edge highlight line of `rgba(0, 229, 255, 0.2)`.
- **Level 3 (Popovers, Modals & Toast Banners)**: `#1E293B` raised with an ambient drop shadow: `0 12px 32px -4px rgba(0, 0, 0, 0.65)`, complemented by a 1px border of `rgba(0, 229, 255, 0.3)`.
- **Neon Halos**: Selected game cards and primary active controls project an electric underglow using `box-shadow: 0 0 20px -2px rgba(0, 229, 255, 0.35)`.

## Shapes

The interface balances sharp gaming-display aesthetics with ergonomic touch forms using a consistent radius scale:

- **Pills (`rounded-full`)**: Genre labels, active member indicators, platform chips (PC, Browser, iOS, Android), and filter toggles.
- **Cards & Sheets (`rounded-lg` / 16px)**: Featured game cards, modal bottom sheets, reward vouchers, and container frames.
- **Interactive Controls (`rounded` / 8px)**: Standard CTA buttons, text fields, search boxes, and drop-down selectors.
- **Media Thumbnails (`rounded` / 8px)**: In-game screenshots, avatar frames, and mini achievement badges.

## Components

### Buttons
- **Primary CTA**: High-visibility Electric Cyan (`#00E5FF`) fill with dark contrast text (`#0B0F17`, `fontWeight: 700`). Features an optional subtle cyan glow (`rgba(0, 229, 255, 0.3)`) during active state.
- **Secondary**: Translucent surface `#1E293B` with border `1px solid rgba(129, 140, 248, 0.4)` and Neon Indigo (`#818CF8`) text.
- **Tertiary / Ghost**: Transparent background with `#94A3B8` icon/label, switching to `#F8FAFC` upon touch down.

### Chips & Gaming Badges
- **Platform Badge**: Fully pill-shaped with background `rgba(30, 41, 59, 0.7)`, border `1px solid rgba(255, 255, 255, 0.08)`, typography `label-sm`, and monochrome hardware logos (Steam, Windows, Web).
- **Status Indicator**: Features a glowing 6px circular dot (`#00E5FF` for Live Drops, `#F43F5E` for Ending Soon) adjacent to `label-md` uppercase text.

### Cards
- **Featured Game Card**: 16:9 aspect-ratio media container topped by an integrated vertical gradient overlay (`rgba(11, 15, 23, 0) 40%` to `rgba(11, 15, 23, 0.95) 100%`). Title, genre pill, and platform compatibility icons overlay directly at the base. Border consists of a hairline `rgba(255, 255, 255, 0.08)`.
- **Compact Game Row**: Horizontal row utilizing a 72x72 rounded thumbnail, stacked headline-sm game title, free tag, and quick-claim icon button.

### Lists & Activity Feeds
- Items separated by an 8px vertical gap rather than hairpins. List items utilize `#131B2A` backgrounds with inset padding of 12px, providing modular modularity.

### Input Fields & Search
- Container `#131B2A` with a 1px border of `#334155`. 
- Active state transitions the border to Electric Cyan (`#00E5FF`) paired with an interior glow `0 0 0 1px #00E5FF`. Text input uses `body-md` in `#F8FAFC`.

### Checkboxes & Radio Controls
- Square with 4px corner radius for checkboxes; circular for radios. Inactive state features a 2px border of `#475569`. Active state transitions to `#00E5FF` fill with a `#0B0F17` inner mark.

### Bottom Navigation & Header Bars
- **Bottom Navigation**: Floating frosted bar (`#0B0F17` at 85% opacity, `backdrop-filter: blur(20px)`), anchored with 12px horizontal inset and 16px bottom margin. Active destination displays an icon wash and indicator dot in `#00E5FF`.
- **Header Bar**: Minimalist top anchor featuring user level avatar, platform network ping counter, and unified search action.