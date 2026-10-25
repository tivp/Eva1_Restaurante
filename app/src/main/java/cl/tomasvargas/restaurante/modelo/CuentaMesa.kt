package cl.tomasvargas.restaurante.modelo

class CuentaMesa(val mesa: Int = 1) {
    private val _items: MutableList<ItemMesa> = mutableListOf()
    val items: List<ItemMesa> get() = _items

    var aceptaPropina: Boolean = true

    fun agregarItem(item: ItemMesa) {
        _items.add(item)
    }

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    fun calcularSubtotal(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    fun calcularTotalComida(): Int {
        return calcularSubtotal()
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalComida() * 0.10).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalComida() + calcularPropina()
    }

    /**
     * Limpia todos los ítems de la cuenta de la mesa.
     */
    fun limpiarItems() {
        _items.clear()
    }
}
