package screens.close_session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.NormalText
import kotlin.system.exitProcess

@Composable
fun CloseSession() {
    Column(modifier = Modifier.fillMaxSize()) {
        NormalText(
            "Неверный пароль",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 24.dp)
        )
        Button(
            onClick = {
                exitProcess(0)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.4f)
        ) {
            Text("Выход")
        }
    }
}