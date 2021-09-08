package screens.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import data.model.UIUser
import ui.NormalText

@Composable
fun ChangePasswordView(viewModel: ChangePasswordViewModel, modifier: Modifier, onSuccess: (UIUser) -> Unit) {
    var pass0 by remember { mutableStateOf("") }
    var pass1 by remember { mutableStateOf("") }
    val result by viewModel.result.collectAsState()

    result?.ifSuccess {
        it?.let(onSuccess)
    }

    Column(modifier) {
        NormalText(
            if (viewModel.strongPassword)
                "Придумайте сложный пароль (Наличие латинских букв и символов кириллицы)"
            else
                "Придумайте пароль",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        if (viewModel.result.value?.status?.isError() == true)
            Text(
                text = "Пароль не соответствует требованию сложности",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Red
            )
        TextField(
            pass0,
            onValueChange = {
                pass0 = it
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        TextField(
            pass1,
            onValueChange = {
                pass1 = it
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        if (result?.status?.isLoading() == true) {
            CircularProgressIndicator(modifier.align(Alignment.CenterHorizontally))
        } else {
            Button(
                onClick = {
                    if (pass0 == pass1)
                        viewModel.edit(pass0)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp),
                enabled = pass0.isNotBlank() && pass0 == pass1
            ) {
                Text("Подтвердить")
            }
        }
    }
}