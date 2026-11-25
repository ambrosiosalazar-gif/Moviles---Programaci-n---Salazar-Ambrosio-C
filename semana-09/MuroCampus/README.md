# MuroCampus

App Android que consume el servicio web REST JSONPlaceholder con Retrofit y arquitectura MVVM.
Lista publicaciones, muestra el detalle con sus comentarios, filtra por usuario y crea una publicación nueva.
También se comporta bien cuando no hay internet.

**Autor:** Ambrosio Salazar

## Estructura de paquetes

com.tecsup.murocampus
├── MainActivity.kt
├── model/
│   └── Modelos.kt (Usuario, Post, Comentario, NuevoPost)
├── data/
│   ├── PostRepository.kt (interface + PostRepositoryRemote)
│   └── remote/
│       ├── PostApi.kt
│       └── RetrofitClient.kt
├── viewmodel/
│   ├── UiState.kt
│   ├── PostsViewModel.kt
│   └── PostsViewModelFactory.kt
└── ui/
├── Componentes.kt
├── PantallaLista.kt
├── PantallaDetalle.kt
└── PantallaCrear.kt

## Arquitectura MVVM + Repository

View (Pantallas) -> ViewModel (PostsViewModel) -> PostRepository (interface) -> PostApi (Retrofit) -> JSONPlaceholder

- La pantalla solo observa el estado y llama funciones del ViewModel.
- El ViewModel usa la interface del Repository y expone un StateFlow de UiState (Cargando, Exito, Error).
- El Repository captura las excepciones de red y devuelve Result.

## Capturas

### Lista
![Lista](capturas/lista.png)

### Detalle con comentarios
![Detalle](capturas/detalle.png)

### Filtro por usuario
![Filtro](capturas/filtro.png)

### Crear publicación
![Crear](capturas/crear.png)

### Error de conexión
![Error](capturas/error.png)

Nota: el texto en latín viene del servicio JSONPlaceholder, que devuelve texto de relleno (lorem ipsum).

## Para pensar

1. **¿Qué diferencia hay entre GET y POST? ¿Por qué crear lleva @Body y porUsuario lleva @Query?**
   GET lee datos del servidor y POST crea un recurso nuevo. Crear lleva @Body porque hay que enviar un objeto JSON en el cuerpo de la petición. porUsuario lleva @Query porque solo agrega un filtro a la URL, como posts?userId=2.

2. **¿Por qué la ruta es "posts" y no "/posts"?**
   Porque la URL base ya termina en "/". Si la ruta empieza con "/", reemplazaría todo el camino de la URL base en lugar de agregarse a ella.

3. **¿Por qué el Repository devuelve Result en vez de dejar que la excepción llegue al ViewModel?**
   Para que el ViewModel no conozca Retrofit ni sus excepciones. El Repository convierte los errores en Result.failure y el ViewModel solo decide qué estado mostrar, sin que la app se caiga.

4. **¿Qué pasa si olvidas el permiso INTERNET? ¿Y si olvidas ignoreUnknownKeys y el servidor agrega un campo nuevo?**
   Sin el permiso la app compila pero nunca se conecta y muestra un error de red aunque haya internet. Sin ignoreUnknownKeys, si el servidor agrega un campo que el modelo no tiene, la lectura del JSON falla con SerializationException.

5. **¿Por qué la pantalla de lista usa un when sobre UiState y no un if con tres variables?**
   Porque UiState es una sealed interface y solo puede estar en un estado a la vez. El when obliga a cubrir todos los casos y evita estados imposibles, como mostrar cargando y error al mismo tiempo.

6. **JSONPlaceholder no guarda los POST. ¿Cómo cambiaría tu app si usaras un servicio real?**
   La publicación creada aparecería al volver a cargar la lista, porque el servidor sí la guardaría. Habría que agregar autenticación si el servicio la exige, y refrescar la lista después de crear.