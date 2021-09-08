package screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.UIUser
import data.repository.RepositoryUser
import screens.LoginViev
import screens.login.LoginViewModel
import screens.users.UsersView
import screens.users.UsersViewModel
import ui.BigText
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
                        Text("О программе", modifier = Modifier.clickable { expanded = !expanded })
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
                            Modifier.fillMaxSize().align(Alignment.CenterVertically),
                            textAlign = TextAlign.Center
                        )
                    }
                    MainState.viewList -> {
                        UsersView(UsersViewModel(RepositoryUser))
                    }
                    MainState.changePassword -> {

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