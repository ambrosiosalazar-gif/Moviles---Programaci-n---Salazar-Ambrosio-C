## Análisis Parte 2 nombre y precio son val porque no deben cambiar una vez creado el producto: el nombre identifica al producto y el precio no debería modificarse directamente sobre el objeto ya creado. cantidad es var porque sí cambia con frecuencia, según lo que el cliente agregue o quite del carrito. Si se intenta hacer producto.precio = 100.0 después de crear el objeto, el compilador marca error, porque precio es val (de solo lectura) y no se puede reasignar una vez inicializado.

## Versión con IA - POO aplicada

Se reestructuró el proyecto aplicando Programación Orientada a Objetos:

- **Abstracción**: la clase Producto es abstracta, no se puede instanciar directamente, solo sus subtipos concretos.
- **Herencia**: Electronico, Alimento y Ropa heredan de Producto.
- **Polimorfismo**: cada subtipo implementa su propio calcularImpuesto() y categoria() de forma distinta (los alimentos no llevan IGV, los demás sí).
- **Encapsulamiento**: los atributos de Producto son privados (_nombre, _precio, _cantidad) y se accede a ellos mediante propiedades controladas (get/set), y la lista de productos dentro de CarritoCompras también es privada.
- El programa ahora pide todos los datos por teclado (cliente, cantidad de productos, nombre, precio, cantidad y categoría de cada producto) en vez de tenerlos fijos en el código.

## Archivos del proyecto (versión POO)
- Producto.kt: clase abstracta Producto y sus subclases Electronico, Alimento, Ropa
- CarritoCompras.kt: clase que administra la lista de productos y los cálculos
- Carrito.kt: función main() que pide los datos por consola y arma el carrito