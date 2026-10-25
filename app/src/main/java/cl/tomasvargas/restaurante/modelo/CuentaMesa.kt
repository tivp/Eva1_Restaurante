package cl.tomasvargas.restaurante.modelo

// Clase que administra los ítems consumidos en la mesa y los totales a pagar
class CuentaMesa(val mesa: Int = 1) {
    private val _items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    // Agrega un objeto ItemMesa directamente a la lista
    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    // Crea y agrega un ItemMesa a partir de un ItemMenu y su cantidad
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    // Suma los subtotales de todos los platillos almacenados en la lista
    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    // Calcula el 10% de propina si aceptaPropina es verdadero
    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.10).toInt() else 0
    }

    // Reutiliza las funciones anteriores para obtener el monto final
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}