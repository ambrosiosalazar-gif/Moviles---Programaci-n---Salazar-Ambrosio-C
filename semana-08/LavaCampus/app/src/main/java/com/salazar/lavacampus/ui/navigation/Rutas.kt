package com.salazar.lavacampus.ui.navigation

sealed class Rutas(val ruta: String) {
    object Splash : Rutas("splash")
    object Login : Rutas("login")
    object InicioEstudiante : Rutas("inicio_estudiante")
    object NuevoPedido : Rutas("nuevo_pedido")
    object DetallePedido : Rutas("detalle_pedido/{pedidoId}") {
        fun crearRuta(pedidoId: Int) = "detalle_pedido/$pedidoId"
    }
    object MisPedidos : Rutas("mis_pedidos")
    object Tarifas : Rutas("tarifas")
    object PerfilEstudiante : Rutas("perfil_estudiante")

    object PanelAdmin : Rutas("panel_admin")
    object BandejaAdmin : Rutas("bandeja_admin")
    object GestionPedido : Rutas("gestion_pedido/{pedidoId}") {
        fun crearRuta(pedidoId: Int) = "gestion_pedido/$pedidoId"
    }
    object ServiciosAdmin : Rutas("servicios_admin")
    object FormularioServicio : Rutas("formulario_servicio?servicioId={servicioId}") {
        fun crearRuta(servicioId: Int? = null) = if (servicioId != null) "formulario_servicio?servicioId=$servicioId" else "formulario_servicio"
    }
    object ClientesAdmin : Rutas("clientes_admin")
    object AjustesAdmin : Rutas("ajustes_admin")
}
