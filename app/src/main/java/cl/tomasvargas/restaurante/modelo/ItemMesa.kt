package cl.tomasvargas.restaurante.modelo

data class ItemMesa(
    val itemMenu: ItemMenu,
    var cantidad: Int
) {
    fun calcularSubtotal(): Int {
        return itemMenu.obtenerPrecioNumerico() * cantidad
    }
}
