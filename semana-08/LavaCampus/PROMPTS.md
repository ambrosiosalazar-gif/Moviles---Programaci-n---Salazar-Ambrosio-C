# Registro de Prompts y Mejoras con IA — LavaCampus (Rama `conIA`)

Este documento detalla las funcionalidades inteligentes incorporadas en la versión mejorada de **LavaCampus** sobre la rama `conIA`, documentando los prompts de asistencia, la arquitectura basada en heurísticas/reglas y la verificación de pruebas.

---

## Índice de Mejoras Implementadas

1. [Etapa 1: Recomendador Inteligente de Lavado](#etapa-1-recomendador-inteligente-de-lavado)
2. [Etapa 2: Smart Notes (Análisis de Notas y Etiquetas Automáticas)](#etapa-2-smart-notes-analisis-inteligente-de-notas-y-etiquetas-automaticas)
3. [Etapa 3: Indicador Inteligente de Congestión para el Administrador](#etapa-3-indicador-inteligente-de-congestion-para-el-administrador)

---

## Nota Metodológica
Todas las funcionalidades inteligentes implementadas operan **100% de manera local y offline**, utilizando algoritmos heurísticos, análisis estadístico de frecuencias sobre la base de datos Room y procesamiento de lenguaje natural basado en reglas de coincidencia de patrones (NLP rule-based), **sin emplear modelos de IA entrenados en la nube ni APIs de pago**.

---

## Etapa 1: Recomendador Inteligente de Lavado

- **Problema que resuelve**: Los estudiantes iniciaban sesión y veían su pedido activo o historial, pero no recibían sugerencias proactivas sobre sus hábitos de lavado anteriores.
- **Objetivo**: Proveer un asistente inteligente en la pantalla de inicio del estudiante que analice su frecuencia de pedidos y determine si es momento de programar un nuevo lavado.
- **Archivos creados y modificados**:
  - `ai/RecomendadorLavado.kt` [NUEVO]
  - `viewmodel/InicioViewModel.kt` [MODIFICADO]
  - `ui/estudiante/InicioScreen.kt` [MODIFICADO]
  - `test/java/com/salazar/lavacampus/RecomendadorLavadoTest.kt` [NUEVO]
- **Algoritmo implementado**:
  - Filtra los pedidos válidos del usuario (excluyendo cancelados).
  - Calcula el intervalo promedio en días entre pedidos consecutivos.
  - Identifica el servicio más solicitado (favorito).
  - Compara los días transcurridos desde el último pedido con la frecuencia estimada del usuario para emitir una recomendación oportuna (normal o urgente). Si el usuario no tiene historial, retorna `null` (sin inventar sugerencias).
- **Cómo probarlo**:
  1. Iniciar sesión como estudiante (`ana@correo.test` / `1234`).
  2. Verificar la tarjeta superior "🤖 Sugerencia Inteligente" en la pantalla de Inicio.
- **Resultados de Pruebas y Compilación**:
  - Pruebas unitarias: `RecomendadorLavadoTest` (Aprobadas).
  - Compilación `assembleDebug`: Exitosa.
- **Commit**: `93e744d ia: agrega recomendador inteligente de lavado en el inicio del estudiante`

---

## Etapa 2: Smart Notes (Análisis Inteligente de Notas y Etiquetas Automáticas)

- **Problema que resuelve**: Las notas libres dejadas por los estudiantes requerían lectura detallada por parte de la administración para identificar indicaciones especiales (ej. ropa delicada, agua fría, urgencia).
- **Objetivo**: Extraer automáticamente etiquetas operativas a partir del texto ingresado por el estudiante.
- **Archivos creados y modificados**:
  - `ai/AnalizadorNotas.kt` [NUEVO]
  - `ui/components/SmartNotesChips.kt` [NUEVO]
  - `test/java/com/salazar/lavacampus/AnalizadorNotasTest.kt` [NUEVO]
  - Vistas y componentes (`PedidoCard`, `NuevoPedidoScreen`, `DetallePedidoScreen`, `GestionPedidoScreen`) [MODIFICADOS]
  - *Bugfix*: Corrección de cierre inesperado durante la escritura en vivo mediante blindaje `try-catch` y reemplazo de contenedores experimentales por `Row`.
- **Algoritmo implementado**:
  - Normalización de texto (eliminación de tildes y conversión a minúsculas).
  - Búsqueda de patrones clave mediante condicionales con lógica de negación (para evitar falsos positivos como `"no es urgente"`).
  - Generación de chips visuales (`🏷️ Urgente`, `🏷️ Agua fría`, `🏷️ Ropa delicada`, etc.).
- **Cómo probarlo**:
  1. Crear un nuevo pedido como estudiante e ingresar una nota descriptiva (ej. *"Urgente, lavar con agua fría"*).
  2. Comprobar la vista previa de etiquetas en vivo y su visualización posterior en el detalle del pedido y en la bandeja del administrador.
- **Resultados de Pruebas y Compilación**:
  - Pruebas unitarias: `AnalizadorNotasTest` (Aprobadas).
  - Compilación `assembleDebug`: Exitosa.
- **Commits**: 
  - `8cd7d53 ia: agrega analisis inteligente de notas y etiquetas automaticas`
  - `2ed1d6d fix(ia): corrige cierre inesperado al escribir notas`

---

## Etapa 3: Indicador Inteligente de Congestión para el Administrador

- **Problema que resuelve**: El panel del administrador mostraba contadores estáticos sin reflejar de manera diagnóstica el nivel de saturación operativa de la lavandería.
- **Objetivo**: Calcular un indicador de congestión en tiempo real basado en la cantidad de pedidos activos (`RECIBIDO`, `EN_PROCESO`, `LISTO`).
- **Archivos creados y modificados**:
  - `ai/AnalizadorCongestion.kt` [NUEVO]
  - `test/java/com/salazar/lavacampus/AnalizadorCongestionTest.kt` [NUEVO]
  - `viewmodel/PanelViewModel.kt` [MODIFICADO]
  - `ui/administrador/PanelScreen.kt` [MODIFICADO]
- **Algoritmo implementado**:
  - Evalúa los pedidos activos y los categoriza en tres niveles:
    - **Bajo** (0-3 pedidos): Operación normal (Verde).
    - **Moderado** (4-7 pedidos): Carga considerable (Amarillo).
    - **Alto** (8+ pedidos): Carga elevada (Rojo).
  - Genera recomendaciones operativas automáticas para el administrador.
- **Cómo probarlo**:
  1. Iniciar sesión como administrador (`admin@lavacampus.test` / `admin`).
  2. Revisar la tarjeta superior "🧠 Indicador Inteligente de Congestión" en el Panel.
- **Resultados de Pruebas y Compilación**:
  - Pruebas unitarias: `AnalizadorCongestionTest` (Aprobadas).
  - Compilación `assembleDebug`: Exitosa.
- **Commit**: `ec32b51 ia: agrega indicador inteligente de congestion en el panel administrativo`

---

## Registro de Prompts de Asistencia (Gemini Agent)

Durante el desarrollo de la rama `conIA`, Gemini Agent colaboró en:
1. **Análisis Arquitectónico**: Definición de la estrategia heurística y local para evitar dependencias en la nube, respetando la arquitectura MVVM.
2. **Implementación de Clases IA**: Estructuración de `RecomendadorLavado`, `AnalizadorNotas` y `AnalizadorCongestion` como clases independientes, puras y altamente testeables.
3. **Resolución de Errores (Bugfix)**: Diagnóstico y corrección del cierre inesperado en recomposición al escribir notas, asegurando estabilidad en UI mediante `Row` y bloques `try-catch`.
4. **Diseño de Pruebas Unitarias**: Creación de los test suites en `src/test/java` validando casos límite y regresiones.

---

## Comparación Funcional (`main` vs `conIA`)

| Característica | Rama `main` (Laboratorio Original) | Rama `conIA` (Versión Inteligente) |
| :--- | :--- | :--- |
| **Inicio Estudiante** | Muestra pedido activo y últimos pedidos estáticos. | Incluye asistente inteligente **Recomendador de Lavado**. |
| **Notas de Pedido** | Texto plano guardado en Room. | Procesamiento automático de texto mediante **Smart Notes** con etiquetas visuales. |
| **Panel Administrador** | Contadores diarios e ingresos semanales estándar. | Incluye **Indicador Inteligente de Congestión** con diagnósticos y recomendaciones. |
| **Pruebas Automatizadas** | Pruebas estándar del proyecto base. | Pruebas unitarias completas para los motores de IA (`12 passed`). |
