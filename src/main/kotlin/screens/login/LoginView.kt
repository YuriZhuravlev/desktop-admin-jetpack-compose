package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import screens.change_password.ChangePasswordView
import screens.change_password.ChangePasswordViewModel
import screens.login.LoginViewModel
import ui.BigText
import ui.NormalText
import kotlin.system.exitProcess

@Composable
fun LoginViev(viewModel: LoginViewModel, onLogin: (UIUser) -> Unit) {
    var count by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val user by viewModel.user.collectAsState()
    val auth by viewModel.auth.collectAsState()
    auth.ifSuccess {
        it?.let(onLogin)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BigText("Вход", modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 24.dp))

        when {
            // Ввод имени
            (user == null || user?.status == Resource.Status.ERROR || (user?.status == Resource.Status.SUCCESS && user?.data == null)) -> {
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
                Button(
                    onClick = {
                        count = 0
                        viewModel.getUser(name)
                    },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Подтвердить")
                }
            }
            // Ввод пароля
            (user?.status == Resource.Status.SUCCESS && count < 3) -> {
                user?.data?.let {
                    if (it.password.isBlank()) {
                        ChangePasswordView(
                            ChangePasswordViewModel(it.id, RepositoryUser),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            viewModel.newPassword()
                        }
                    } else {
                        NormalText("Введите пароль", modifier = Modifier.align(Alignment.CenterHorizontally))
                        if (auth.status.isError()) {
                            Text("Неверный пароль")
                        }
                        TextField(
                            password,
                            onValueChange = {
                                password = it
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                    Button(
                        onClick = {
                            count += 1
                            viewModel.checkPassword(password)
                        },
                        enabled = name.isNotBlank(),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Подтвердить")
                    }
                }
            }
            // Загрузка
            (user?.status == Resource.Status.LOADING) -> {
                NormalText("Введите своё имя", modifier = Modifier.align(Alignment.CenterHorizontally))
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                exitProcess(0)
            }
        }
    }
}