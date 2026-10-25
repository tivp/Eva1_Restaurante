package cl.tomasvargas.restaurante.modelo

// Clase que representa un platillo solicitado en la mesa y su cantidad
data class ItemMesa(
    val itemMenu: ItemMenu,
    var cantidad: Int
) {
    // Calcula el subtotal multiplicando el precio unitario por la cantidad
    fun calcularSubtotal(): Int {
        return itemMenu.obtenerPrecioNumerico() * cantidad
    }
}