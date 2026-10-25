package cl.tomasvargas.restaurante.modelo

// Clase que representa un platillo del menú del restaurante
data class ItemMenu(
    val nombre: String,
    val precio: String
) {
    // Convierte el precio en formato texto ("$12.000") a entero (12000)
    fun obtenerPrecioNumerico(): Int {
        return precio.replace("$", "").replace(".", "").trim().toIntOrNull() ?: 0
    }
}