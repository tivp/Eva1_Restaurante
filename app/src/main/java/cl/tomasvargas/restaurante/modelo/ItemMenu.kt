package cl.tomasvargas.restaurante.modelo

data class ItemMenu(
    val nombre: String,
    val precio: String
) {
    fun obtenerPrecioNumerico(): Int {
        return precio.replace("$", "").replace(".", "").trim().toIntOrNull() ?: 0
    }
}
