package cl.tomasvargas.restaurante

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.tomasvargas.restaurante.modelo.CuentaMesa
import cl.tomasvargas.restaurante.modelo.ItemMenu
import cl.tomasvargas.restaurante.modelo.ItemMesa
import java.text.NumberFormat
import java.util.Locale

/**
 * Actividad principal de la aplicación "Eva1_Restaurante".
 * Gestiona la interfaz de usuario para la toma de pedidos de la mesa,
 * escuchando eventos en tiempo real y calculando subtotales, propina (10%) y total final.
 */
class MainActivity : AppCompatActivity() {

    // Declaración de variables para enlazar los controles de las Vistas XML
    private lateinit var etCantPastel: EditText
    private lateinit var etCantCazuela: EditText
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvComidaMonto: TextView
    private lateinit var tvPropinaMonto: TextView
    private lateinit var tvTotalMonto: TextView
    private lateinit var switchPropina: Switch

    // Objeto inmutable (val) para formatear los montos numéricos a Pesos Chilenos (CLP)
    private val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    /**
     * Método del ciclo de vida que se ejecuta al crear la Activity.
     * Inicializa la vista, recupera los widgets y configura los escuchadores de eventos.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asocia el archivo de diseño XML (activity_main.xml) con esta Activity
        setContentView(R.layout.activity_main)

        // Vinculación de los controles XML mediante sus identificadores (R.id)
        etCantPastel = findViewById(R.id.etCantPastel)
        etCantCazuela = findViewById(R.id.etCantCazuela)
        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvComidaMonto = findViewById(R.id.tvComidaMonto)
        tvPropinaMonto = findViewById(R.id.tvPropinaMonto)
        tvTotalMonto = findViewById(R.id.tvTotalMonto)
        switchPropina = findViewById(R.id.switchPropina)

        // Gestión del evento TextChanged para el campo de cantidad de Pastel de Choclo.
        // Actualiza los montos automáticamente apenas el mesero modifica el número.
        etCantPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calcularYActualizar()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Gestión del evento TextChanged para el campo de cantidad de Cazuela.
        // Recalcula inmediatamente cuando cambia el texto ingresado.
        etCantCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calcularYActualizar()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Gestión del evento CheckedChange sobre el Switch de propina.
        // Detecta cuando se activa o desactiva el 10% de propina voluntaria y recalcula el total.
        switchPropina.setOnCheckedChangeListener { _, _ ->
            calcularYActualizar()
        }

        // Llamada inicial para mostrar los valores calculados al abrir la aplicación
        calcularYActualizar()
    }

    /**
     * Función encargada de capturar las cantidades ingresadas, instanciar las clases
     * del paquete modelo, realizar los cálculos matemáticos y actualizar la pantalla
     * aplicando el formato de moneda chilena.
     */
    private fun calcularYActualizar() {
        // 1. Obtiene las cantidades ingresadas en los EditText.
        // Se usa toIntOrNull() junto al operador Elvis (?: 0) para evitar errores si el campo queda vacío.
        val cantPastel = etCantPastel.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCantCazuela.text.toString().toIntOrNull() ?: 0

        // 2. Instancia los platillos ofrecidos por el restaurante usando la clase modelo ItemMenu
        val platoPastel = ItemMenu("Pastel de Choclo", "$12.000")
        val platoCazuela = ItemMenu("Cazuela", "$10.000")

        // 3. Asocia cada platillo con la cantidad solicitada en la mesa mediante la clase ItemMesa
        val itemPastelMesa = ItemMesa(platoPastel, cantPastel)
        val itemCazuelaMesa = ItemMesa(platoCazuela, cantCazuela)

        // 4. Calcula los subtotales individuales por especialidad invocando el método de ItemMesa
        val subtotalPastel = itemPastelMesa.calcularSubtotal()
        val subtotalCazuela = itemCazuelaMesa.calcularSubtotal()

        // 5. Muestra los subtotales formateados como pesos chilenos en sus respectivos TextView
        tvSubtotalPastel.text = formatoMoneda.format(subtotalPastel)
        tvSubtotalCazuela.text = formatoMoneda.format(subtotalCazuela)

        // 6. Registra los ítems en CuentaMesa (uso de lista MutableList según diagrama UML)
        // y calcula los montos generales de comida, propina (10% opcional) y total final.
        val cuentaMesa = CuentaMesa(mesa = 1)
        cuentaMesa.aceptaPropina = switchPropina.isChecked
        cuentaMesa.agregarItem(itemPastelMesa)
        cuentaMesa.agregarItem(itemCazuelaMesa)

        val totalComida = cuentaMesa.calcularTotalSinPropina()
        val propina = cuentaMesa.calcularPropina()
        val totalFinal = cuentaMesa.calcularTotalConPropina()

        // 7. Actualiza en pantalla los textos del resumen inferior con formato de moneda chilena
        tvComidaMonto.text = formatoMoneda.format(totalComida)
        tvPropinaMonto.text = formatoMoneda.format(propina)
        tvTotalMonto.text = formatoMoneda.format(totalFinal)
    }
}