package screens.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import ui.NormalText

@Composable
fun ChangePasswordView(viewModel: ChangePasswordViewModel, modifier: Modifier, onSuccess: () -> Unit) {
    var pass0 by remember { mutableStateOf("") }
    var pass1 by remember { mutableStateOf("") }
    val result by viewModel.result.collectAsState()

    if (result) onSuccess()

    Column(modifier) {
        NormalText("Придумайте пароль", modifier = Modifier.align(Alignment.CenterHorizontally))
        TextField(
            pass0,
            onValueChange = {
                pass0 = it
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            visualTransformation = PasswordVisualTransformation()
        )
        TextField(
            pass1,
            onValueChange = {
                pass1 = it
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {

        }) {
            Text("Подтвердить")
        }
    }
}