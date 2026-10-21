package com.salazar.lavacampus.model

object Casilleros {
    val todos: List<String> = (1..30).map { "C-%02d".format(it) }
}
