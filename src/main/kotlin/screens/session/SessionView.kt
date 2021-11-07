package screens.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import data.model.SessionState
import ui.NormalText
import java.security.Key

@Composable
fun SessionView(viewModel: SessionViewModel, onChange: (SessionState, Key) -> Unit) {
    var password by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        NormalText(
            "Введите парольную фразу для расшифрования",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 24.dp)
        )
        TextField(
            password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f)
                .padding(vertical = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                val key = utils.getKey(password)
                viewModel.checkPassword(key) {
                    onChange(
                        if (it) SessionState.SESSION else SessionState.CLOSE_SESSION,
                        key
                    )
                }
            },
            enabled = password.isNotBlank(),
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.4f)
        ) {
            Text("Подтвердить")
        }
    }
}