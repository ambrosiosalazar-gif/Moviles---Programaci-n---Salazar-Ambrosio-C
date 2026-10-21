package com.salazar.lavacampus.data.local

import com.salazar.lavacampus.data.local.entity.DetallePedido
import com.salazar.lavacampus.data.local.entity.Pedido
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.data.local.entity.Usuario
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.model.Rol
import com.salazar.lavacampus.model.Unidad
import java.time.LocalDate

object DatosIniciales {
    suspend fun sembrar(db: LavaDatabase) {
        if (db.usuarioDao().contar() > 0) return

        val servicios = listOf(
            Servicio(1, "Lavado y secado", "Lavado por kilo con secado rápido", Unidad.KG.name, 8.0, 24, true, "wash"),
            Servicio(2, "Planchado", "Planchado por prenda de vestir", Unidad.PRENDA.name, 2.5, 48, true, "iron"),
            Servicio(3, "Lavado en seco", "Tratamiento especial para prendas delicadas", Unidad.PRENDA.name, 12.0, 48, true, "shirt"),
            Servicio(4, "Edredón / frazada", "Lavado de edredones y frazadas de cualquier tamaño", Unidad.UNIDAD.name, 20.0, 48, false, "wash")
        )
        db.servicioDao().insertarTodos(servicios)

        val usuarios = listOf(
            Usuario(1, "Ana Gómez", "U20231001", "ana@correo.test", "1234", Rol.CLIENTE.name),
            Usuario(2, "Luis Torres", "U20231002", "luis@correo.test", "1234", Rol.CLIENTE.name),
            Usuario(3, "Marta Silva", "U20231003", "marta@correo.test", "1234", Rol.CLIENTE.name),
            Usuario(4, "Administrador LavaCampus", "", "admin@lavacampus.test", "admin", Rol.ADMIN.name)
        )
        db.usuarioDao().insertarTodos(usuarios)

        val hoy = LocalDate.now()
        val ayer = hoy.minusDays(1)
        val hace2dias = hoy.minusDays(2)
        val hace3dias = hoy.minusDays(3)

        val p1Id = db.pedidoDao().insertar(
            Pedido(1, 1, hace3dias.toString(), hace2dias.toString(), EstadoPedido.ENTREGADO.name, 28.0, "C-01", true, "Entregado a tiempo")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p1Id.toInt(), 1, 3.0, 8.0, 24.0),
            DetallePedido(0, p1Id.toInt(), 2, 2.0, 2.5, 5.0)
        ))

        val p2Id = db.pedidoDao().insertar(
            Pedido(2, 1, ayer.toString(), hoy.toString(), EstadoPedido.LISTO.name, 16.0, "C-05", false, "Por favor doblar bien")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p2Id.toInt(), 1, 2.0, 8.0, 16.0)
        ))

        val p3Id = db.pedidoDao().insertar(
            Pedido(3, 1, hoy.toString(), hoy.plusDays(1).toString(), EstadoPedido.RECIBIDO.name, 21.0, null, false, "")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p3Id.toInt(), 1, 2.0, 8.0, 16.0),
            DetallePedido(0, p3Id.toInt(), 2, 2.0, 2.5, 5.0)
        ))

        val p4Id = db.pedidoDao().insertar(
            Pedido(4, 2, hace2dias.toString(), ayer.toString(), EstadoPedido.LISTO.name, 24.0, "C-07", true, "Pago por Yape")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p4Id.toInt(), 3, 2.0, 12.0, 24.0)
        ))

        val p5Id = db.pedidoDao().insertar(
            Pedido(5, 2, ayer.toString(), hoy.toString(), EstadoPedido.EN_PROCESO.name, 10.0, null, false, "")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p5Id.toInt(), 2, 4.0, 2.5, 10.0)
        ))

        val p6Id = db.pedidoDao().insertar(
            Pedido(6, 3, hace3dias.toString(), hace2dias.toString(), EstadoPedido.ENTREGADO.name, 32.0, "C-02", true, "")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p6Id.toInt(), 1, 4.0, 8.0, 32.0)
        ))

        val p7Id = db.pedidoDao().insertar(
            Pedido(7, 3, hoy.toString(), hoy.plusDays(2).toString(), EstadoPedido.RECIBIDO.name, 12.0, null, false, "Sin almidón")
        )
        db.detalleDao().insertarTodos(listOf(
            DetallePedido(0, p7Id.toInt(), 3, 1.0, 12.0, 12.0)
        ))
    }
}
