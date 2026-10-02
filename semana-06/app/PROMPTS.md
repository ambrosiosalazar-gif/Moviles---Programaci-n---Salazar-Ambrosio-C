# PROMPTS.md - Laboratorio 06 (Fase 2: mejora con IA)

Alumno: Ambrosio Salazar
Rama: mejora-ia

## Prompt 1: Favoritos desde el menú de cada producto

**Prompt:**
> Ya tengo mi tienda con el DropdownMenu en cada producto y el drawer funcionando. Ahora quiero que cuando toque "Favoritos" en el menú de un producto, ese producto se marque como favorito, y que el drawer se entere de eso. Explícame paso a paso cómo hacerlo.

**Respuesta resumida:**
La IA me dijo que guarde la lista de favoritos en un solo lugar (en `AppNavegacion`) usando `mutableStateListOf<Int>()` con los ids de los productos, y que se la pase a `PantallaInicio` y a `TarjetaProducto`. También hizo que la opción del menú cambie entre "Favoritos" y "Quitar de favoritos" con el corazón lleno o vacío.

**Qué tuve que corregir:**
Me dio los cambios por partes y me confundí, así que le pasé mi código de `PantallaInicio.kt` y `AppNavegacion.kt` y le pedí que me devolviera los archivos completos para reemplazarlos. (Agrega aquí cualquier otro error que te haya salido.)

## Prompt 2: Que se vea como el ejemplo del profe

**Prompt:**
> Mi profe nos mandó un ejemplo con la tienda más colorida (morado). ¿No tendría que cambiar la app para que se vea así? Te mando la captura del ejemplo.

**Respuesta resumida:**
La IA vio la captura y me ayudó a copiar el estilo: creó `ColoresTecsup.kt` con los colores morado y lavanda, cambió la barra superior (título "TECSUP Store" y subtítulo "Más vendidos"), las tarjetas con ícono de bolsa y el ⋮ en círculo blanco, y el drawer con las iniciales en círculo, el ítem activo resaltado y la opción "Cerrar sesión".

**Qué tuve que corregir:**
(Escribe aquí lo que te pasó de verdad. Por ejemplo, si algún ícono o import salió en rojo y cómo lo arreglaste. Si no pasó nada, escribe "Nada, funcionó a la primera".)

## Prompt 3: Badge con contador en Favoritos

**Prompt:**
> Ahora falta lo del badge: quiero que en el drawer, en la opción "Favoritos", aparezca un número con la cantidad de productos que marqué como favoritos desde el menú de cada producto.

**Respuesta resumida:**
La IA le agregó a `AppDrawer` un parámetro `favoritosCount` y un `badge` en el `NavigationDrawerItem` que solo se muestra en "Favoritos" cuando hay al menos 1 favorito. En `AppNavegacion` se le pasa `favoritos.size`. Como la lista es de estado, el número se actualiza solo al agregar o quitar favoritos.

**Qué tuve que corregir:**
(Escribe aquí lo que hiciste. Por ejemplo: que tuve que reemplazar el archivo completo de `AppNavegacion.kt` porque la IA solo me indicó agregar una línea y no la encontraba bien.)