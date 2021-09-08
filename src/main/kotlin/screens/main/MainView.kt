package screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.UIUser
import data.repository.RepositoryUser
import screens.LoginViev
import screens.about.AboutView
import screens.add_user.AddUserView
import screens.add_user.AddUserViewModel
import screens.change_password.ChangePasswordView
import screens.change_password.ChangePasswordViewModel
import screens.login.LoginViewModel
import screens.users.UsersView
import screens.users.UsersViewModel
import ui.BigText
import ui.NormalText
import kotlin.system.exitProcess

@Composable
fun MainView(viewModel: MainViewModel) {
    val profile by viewModel.profile.collectAsState()
    val state by viewModel.state.collectAsState()

    if (profile == null)
        LoginViev(LoginViewModel(RepositoryUser)) {
            viewModel.login(it)
        }
    else {
        // Пользователь авторизован
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth().shadow(8.dp).background(Color.White)) {
                // menu
                Box {
                    var expanded by remember { mutableStateOf(false) }
                    Text("Файл", modifier = Modifier.clickable { expanded = !expanded }.padding(2.dp))
                    DropdownMenu(
                        expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        profile?.isAdmin?.let {
                            ActionMenu(it, viewModel)
                        }
                    }
                }
                Box {
                    var expanded by remember { mutableStateOf(false) }
                    Text("Помощь", modifier = Modifier.clickable { expanded = !expanded }.padding(2.dp))
                    DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                        Text("О программе", modifier = Modifier.clickable {
                            expanded = !expanded
                            viewModel.postState(MainState.about)
                        })
                    }
                }
            }
            BigText("Username: ${profile?.username}", Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
            Row(Modifier.fillMaxSize()) {
                profile?.isAdmin?.let {
                    ActionMenu(it, viewModel)
                }
                Box(Modifier.fillMaxHeight().width(1.dp).background(Color.Gray))
                when (state) {
                    MainState.empty -> {
                        Text(
                            "Выберите действие",
                            Modifier.fillMaxSize().align(Alignment.CenterVertically).padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    MainState.viewList -> {
                        UsersView(UsersViewModel(RepositoryUser))
                    }
                    MainState.changePassword -> {
                        var accept by remember { mutableStateOf(false) }
                        if (!accept) {
                            var pass0 by remember { mutableStateOf("") }
                            var error by remember { mutableStateOf(false) }
                            Column(Modifier.padding(horizontal = 8.dp).fillMaxWidth()) {
                                NormalText(
                                    "Введите старый пароль перед изменением",
                                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(0.5f)
                                        .align(Alignment.CenterHorizontally)
                                )
                                TextField(
                                    pass0,
                                    onValueChange = {
                                        pass0 = it
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 8.dp)
                                        .fillMaxWidth(0.5f).align(Alignment.CenterHorizontally),
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                if (error) {
                                    Text(
                                        "Неверный пароль!",
                                        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth(0.4f)
                                            .align(Alignment.CenterHorizontally),
                                        color = Color.Red
                                    )
                                }
                                Button(
                                    onClick = {
                                        if (pass0 == profile?.password) {
                                            accept = true
                                        } else {
                                            error = true
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(0.4f).align(Alignment.CenterHorizontally),
                                    enabled = pass0.isNotBlank()
                                ) {
                                    Text("Подтвердить")
                                }
                            }
                        } else {
                            profile?.let {
                                ChangePasswordView(
                                    ChangePasswordViewModel(it, RepositoryUser),
                                    Modifier.fillMaxSize()
                                ) {
                                    viewModel.postState(MainState.empty)
                                    viewModel.login(it)
                                }
                            }
                        }
                    }
                    MainState.addUser -> {
                        AddUserView(
                            AddUserViewModel(RepositoryUser),
                            Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                        ) {
                            viewModel.postState(MainState.empty)
                        }
                    }
                    MainState.about -> {
                        AboutView(Modifier.fillMaxSize()) {
                            viewModel.postState(MainState.empty)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionMenu(isAdmin: Boolean, viewModel: MainViewModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        if (isAdmin) {
            Text("Список пользователей", modifier = Modifier.clickable { viewModel.postState(MainState.viewList) })
            Text("Добавить пользователя", modifier = Modifier.clickable { viewModel.postState(MainState.addUser) })
        }
        Text("Смена пароля", modifier = Modifier.clickable { viewModel.postState(MainState.changePassword) })
        Text("Выход из аккаунта", modifier = Modifier.clickable { viewModel.logout() })
        Text("Завершение программы", modifier = Modifier.clickable { exitProcess(0) })
    }
}

val UIUser.isAdmin get() = username == ADMIN
private const val ADMIN = "ADMIN"