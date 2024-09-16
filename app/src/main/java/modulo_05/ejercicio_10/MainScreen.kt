package modulo_05.ejercicio_10


import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import modulo_05.ejercicio_10.components.Alert
import modulo_05.ejercicio_10.components.MainButton
import modulo_05.ejercicio_10.components.MySegmentedButton
import modulo_05.ejercicio_10.components.MyText
import modulo_05.ejercicio_10.components.MyTextField

import modulo_05.ejercicio_10.viewmodel.MainViewModel


@Composable
fun MainScreen(paddingValues: PaddingValues, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),// Padding del Scaffold

        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val edad by viewModel.edad.collectAsState(initial = "")
        val peso by viewModel.peso.collectAsState(initial = "")
        val altura by viewModel.altura.collectAsState(initial = "")
        val imc by viewModel.imc.collectAsState(initial = "")
        val showAlert by viewModel.showAlert.collectAsState(initial = false)


        MyText(text = stringResource(id = R.string.tituloApp))
        MySegmentedButton()
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            MyTextField(
                text = edad.toString(),
                onValueChange = {
                    viewModel.onMainScreenChanged(
                        edad = it.toString(),
                        peso.toString(),
                        altura.toString(),

                        )
                },
                label = stringResource(id = R.string.edad)
            )
            MyTextField(
                text = peso.toString(),
                onValueChange = {peso ->
                    viewModel.onMainScreenChanged(
                        edad.toString(),
                        peso = peso,
                        altura = altura.toString()
                    )
                },
                label = stringResource(id = R.string.peso)
            )
            MyTextField(
                text = altura.toString(),
                onValueChange = {
                    viewModel.onMainScreenChanged(
                        edad.toString(),
                        peso = peso.toString(),
                        altura = it
                    )
                },
                label = stringResource(id = R.string.altura)
            )

            Text(text = stringResource(id = R.string.calcular))
            Text(text = imc.toString(), fontSize = 50.sp, fontWeight = FontWeight.SemiBold)


            MainButton(text = "Limpiar pantalla", color = MaterialTheme.colorScheme.error) {
                viewModel.limpiarPantalla()
            }

            if (showAlert) {
                AlertDialog(
                    onDismissRequest = { viewModel.upDateShowAlert(false) },
                    title = { Text("Alerta") },
                    text = { Text("La edad debe estar entre 1 y 120") },
                    confirmButton = {
                        Button(onClick = { viewModel.upDateShowAlert(false)}) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}

