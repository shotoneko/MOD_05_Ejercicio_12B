package modulo_05.ejercicio_10.viewmodel

import android.icu.text.DecimalFormat
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _showAlert = MutableStateFlow<Boolean>(false)
    var showAlert: StateFlow<Boolean> = _showAlert

    private val _edad = MutableStateFlow("")
    val edad: StateFlow<String> = _edad.asStateFlow()

    private val _peso = MutableStateFlow(0.0)
    val peso: StateFlow<Double> = _peso.asStateFlow()

    private val _altura = MutableStateFlow(0)
    val altura: StateFlow<Int> = _altura.asStateFlow()

    private var _imc = MutableStateFlow(0.0)
    var imc: StateFlow<Double> = _imc.asStateFlow()

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
    fun upDateShowAlert(value: Boolean){
        _showAlert.value = value
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
                if (edad.toInt() in 1..120) {
                    _showAlert.value = false
                } else {
                    _showAlert.value = true
                }

            } catch (e: NumberFormatException) {

            }
        }
    }



}
