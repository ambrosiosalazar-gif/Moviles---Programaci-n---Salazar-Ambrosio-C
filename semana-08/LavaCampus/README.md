# LavaCampus - Lavandería Universitaria

Aplicación Android desarrollada en **Kotlin** y **Jetpack Compose (Material 3)** bajo la arquitectura **MVVM + Repository** y persistencia local con **Room** y **DataStore**.

## Arquitectura (MVVM + Repository)

```mermaid
graph TD
    View[View / Composable] -->|Eventos / collectAsState| ViewModel[ViewModel / StateFlow]
    ViewModel -->|Llamadas / Flow| Repo[LavaRepository (interface)]
    Repo -->|Implementación| RepoRoom[LavaRepositoryRoom]
    RepoRoom -->|DAO| RoomDB[(Room Database)]
    Prefs[DataStore Preferences] --> ViewModel
```

## Estructura de Paquetes (`com.salazar.lavacampus`)

- `data/local/`: Entidades Room (`Usuario`, `Servicio`, `Pedido`, `DetallePedido`), DAOs y relaciones (`PedidoConDetalle`, `DetalleConServicio`).
- `data/repository/`: Interfaz `LavaRepository` e implementación `LavaRepositoryRoom`.
- `data/prefs/`: `PreferenciasStore` (DataStore Preferences).
- `model/`: Enums (`Rol`, `EstadoPedido`, `Unidad`) y modelos auxiliares.
- `viewmodel/`: ViewModels para cada flujo con manejo de StateFlow.
- `ui/`: Componentes Material 3, gráficos en Canvas, navegación (`NavLava`, `Rutas`) y pantallas separadas por perfil (`estudiante/`, `administrador/`).

## Verificación de Pruebas y Compilación
- Compilación exitosa con Gradle (`assembleDebug`).
- Pruebas de aceptación superadas para los flujos de Estudiante y Administrador.
