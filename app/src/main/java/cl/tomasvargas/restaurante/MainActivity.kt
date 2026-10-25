package cl.tomasvargas.restaurante

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.tomasvargas.restaurante.modelo.ItemMenu
import cl.tomasvargas.restaurante.modelo.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etCantPastel: EditText
    private lateinit var etCantCazuela: EditText
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvComidaMonto: TextView
    private lateinit var tvPropinaMonto: TextView
    private lateinit var tvTotalMonto: TextView
    private lateinit var switchPropina: Switch

    private val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCantPastel = findViewById(R.id.etCantPastel)
        etCantCazuela = findViewById(R.id.etCantCazuela)
        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvComidaMonto = findViewById(R.id.tvComidaMonto)
        tvPropinaMonto = findViewById(R.id.tvPropinaMonto)
        tvTotalMonto = findViewById(R.id.tvTotalMonto)
        switchPropina = findViewById(R.id.switchPropina)

        etCantPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { calcularYActualizar() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etCantCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { calcularYActualizar() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        switchPropina.setOnCheckedChangeListener { _, _ ->
            calcularYActualizar()
        }

        calcularYActualizar()
    }

    private fun calcularYActualizar() {
        val cantPastel = etCantPastel.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCantCazuela.text.toString().toIntOrNull() ?: 0

        val platoPastel = ItemMenu("Pastel de Choclo", "$12.000")
        val platoCazuela = ItemMenu("Cazuela", "$10.000")

        val itemPastelMesa = ItemMesa(platoPastel, cantPastel)
        val itemCazuelaMesa = ItemMesa(platoCazuela, cantCazuela)

        val subtotalPastel = itemPastelMesa.calcularSubtotal()
        val subtotalCazuela = itemCazuelaMesa.calcularSubtotal()

        tvSubtotalPastel.text = formatoMoneda.format(subtotalPastel)
        tvSubtotalCazuela.text = formatoMoneda.format(subtotalCazuela)

        val totalComida = subtotalPastel + subtotalCazuela
        val propina = if (switchPropina.isChecked) (totalComida * 0.10).toInt() else 0
        val totalFinal = totalComida + propina

        tvComidaMonto.text = formatoMoneda.format(totalComida)
        tvPropinaMonto.text = formatoMoneda.format(propina)
        tvTotalMonto.text = formatoMoneda.format(totalFinal)
    }
}