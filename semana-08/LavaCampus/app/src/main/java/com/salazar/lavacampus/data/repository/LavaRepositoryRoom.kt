package com.salazar.lavacampus.data.repository

import androidx.room.withTransaction
import com.salazar.lavacampus.data.local.LavaDatabase
import com.salazar.lavacampus.data.local.entity.DetallePedido
import com.salazar.lavacampus.data.local.entity.Pedido
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.data.local.entity.Usuario
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.model.ClienteResumen
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.model.IngresoServicio
import com.salazar.lavacampus.model.LineaPedido
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LavaRepositoryRoom(private val db: LavaDatabase) : LavaRepository {
    private val usuarioDao = db.usuarioDao()
    private val servicioDao = db.servicioDao()
    private val pedidoDao = db.pedidoDao()
    private val detalleDao = db.detalleDao()

    override suspend fun login(correo: String, clave: String): Usuario? {
        return usuarioDao.login(correo, clave)
    }

    override fun observarClientes(): Flow<List<ClienteResumen>> {
        return usuarioDao.observarClientes()
    }

    override fun observarServicios(): Flow<List<Servicio>> {
        return servicioDao.observarTodos()
    }

    override fun observarServiciosActivos(): Flow<List<Servicio>> {
        return servicioDao.observarActivos()
    }

    override suspend fun guardarServicio(s: Servicio) {
        if (s.id == 0) {
            servicioDao.insertar(s)
        } else {
            servicioDao.actualizar(s)
        }
    }

    override suspend fun eliminarServicio(s: Servicio) {
        servicioDao.eliminar(s)
    }

    override suspend fun cambiarActivo(id: Int, activo: Boolean) {
        servicioDao.cambiarActivo(id, activo)
    }

    override suspend fun servicioTieneDetalles(id: Int): Boolean {
        return detalleDao.contarPorServicio(id) > 0
    }

    override suspend fun crearPedido(
        usuarioId: Int,
        lineas: List<LineaPedido>,
        fechaEntrega: String,
        nota: String
    ): Long = db.withTransaction {
        var total = 0.0
        val detallesAInsertar = mutableListOf<Triple<Int, Double, Double>>()

        for (linea in lineas) {
            val servicio = servicioDao.obtener(linea.servicioId) ?: continue
            val subtotal = linea.cantidad * servicio.precio
            total += subtotal
            detallesAInsertar.add(Triple(servicio.id, linea.cantidad, servicio.precio))
        }

        val nuevoPedido = Pedido(
            usuarioId = usuarioId,
            fechaRegistro = LocalDate.now().toString(),
            fechaEntrega = fechaEntrega,
            estado = EstadoPedido.RECIBIDO.name,
            total = total,
            casillero = null,
            pagado = false,
            nota = nota
        )

        val pedidoId = pedidoDao.insertar(nuevoPedido)

        val detalles = detallesAInsertar.map { (servicioId, cantidad, precio) ->
            DetallePedido(
                pedidoId = pedidoId.toInt(),
                servicioId = servicioId,
                cantidad = cantidad,
                precioUnitario = precio,
                subtotal = cantidad * precio
            )
        }

        detalleDao.insertarTodos(detalles)
        pedidoId
    }

    override suspend fun contarActivos(usuarioId: Int): Int {
        return pedidoDao.contarActivos(usuarioId)
    }

    override fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>> {
        return pedidoDao.observarPorUsuario(usuarioId)
    }

    override fun observarPedidos(): Flow<List<PedidoConDetalle>> {
        return pedidoDao.observarTodos()
    }

    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> {
        return pedidoDao.observarUno(id)
    }

    override suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?) {
        pedidoDao.actualizarEstado(id, estado.name, casillero)
    }

    override suspend fun marcarPagado(id: Int, pagado: Boolean) {
        pedidoDao.actualizarPagado(id, pagado)
    }

    override fun observarCasillerosOcupados(): Flow<List<String>> {
        return pedidoDao.observarCasillerosOcupados()
    }

    override fun observarPedidosDeFecha(fecha: String): Flow<Int> {
        return pedidoDao.contarPorFecha(fecha)
    }

    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> {
        return pedidoDao.contarPorEstado(estado.name)
    }

    override fun observarIngresosDesde(fechaInicio: String): Flow<Double> {
        return pedidoDao.ingresosDesde(fechaInicio)
    }

    override fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>> {
        return detalleDao.observarIngresosPorServicio(fechaInicio)
    }
}
