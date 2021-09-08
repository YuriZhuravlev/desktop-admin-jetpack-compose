package screens

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
import androidx.compose.ui.unit.dp
import data.Resource
import screens.login.LoginViewModel
import ui.BigText
import ui.NormalText
import kotlin.system.exitProcess

@Composable
fun LoginViev(viewModel: LoginViewModel) {
    var count by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    val user by viewModel.user.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        BigText("Вход", modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 24.dp))

        when {
            // Ввод имени
            (user == null || (count < 3 && (user?.status == Resource.Status.ERROR) || (user?.status == Resource.Status.SUCCESS && user?.data == null))) -> {
                System.out.println("count: $count")
                NormalText("Введите своё имя", modifier = Modifier.align(Alignment.CenterHorizontally))
                TextField(
                    name,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f)
                        .padding(vertical = 16.dp),
                    singleLine = true
                )
                Button(onClick = {
                    count += 1
                    viewModel.getUser(name)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("Подтвердить")
                }
            }
            (user?.status == Resource.Status.SUCCESS) -> {
                user?.data?.let {
                    if (it.password.isBlank()) {
                        NormalText("Придумайте пароль", modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else {
                        NormalText("Введите пароль", modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
            (user?.status == Resource.Status.LOADING) -> {
                Text("loading")
            }
            else -> {
                exitProcess(0)
            }
        }
    }
}