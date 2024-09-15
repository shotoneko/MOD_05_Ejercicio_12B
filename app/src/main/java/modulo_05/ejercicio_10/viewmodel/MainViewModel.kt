package modulo_05.ejercicio_10.viewmodel

import android.icu.text.DecimalFormat
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val _showAlert = MutableLiveData<Boolean>()
   // var showAlert: LiveData<Boolean> = _showAlert

    private val _edad = MutableLiveData<String>()
    var edad: LiveData<String> = _edad

    private val _peso = MutableLiveData<Double>()
    val peso: LiveData<Double> = _peso

    private val _altura = MutableLiveData<Int>()
    val altura: LiveData<Int> = _altura

    private var _imc = MutableLiveData<Double>()
    var imc: LiveData<Double> = _imc

    fun onMainScreenChanged(edad: String, peso: String, altura: String){
        _edad.value = edad
        validaEdad(edad)
        try {
            _peso.value = peso.toDouble()
            _altura.value = altura.toInt()

            if (!peso.isBlank() && !altura.isBlank() && altura.toInt() !=0) {
                _imc.value = calcularIMC(peso.toDouble(), altura.toDouble())
            }
        } catch (e: NumberFormatException) {

        }


    }

    fun calcularIMC(peso: Double, altura: Double): Double {
        val imc = peso / (altura * altura) * 10000
        val df = DecimalFormat("#.##")
        val imcFormateado = df.format(imc)
        return imcFormateado.toDouble()
    }

    fun limpiarPantalla() {
        _edad.value = ""
        _peso.value = 0.0
        _altura.value = 0
        _imc.value = 0.0

    }

    fun validaEdad(edad: String) {
        if (edad.length >= 4) {
            _edad.value = edad.substring(0, 3)
        } else {

            try {
                if (edad.toInt() < 1 || edad.toInt() > 120) {
                    _showAlert.value = true
                } else {
                    _showAlert.value = false
                }

            } catch (e: NumberFormatException) {

            }
        }
    }
}
