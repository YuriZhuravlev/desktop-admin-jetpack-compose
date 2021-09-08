package screens.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import data.repository.RepositoryUser
import screens.LoginViev
import screens.login.LoginViewModel

@Composable
fun MainView(viewModel: MainViewModel) {
    val profile by viewModel.profile.collectAsState()

    if (profile == null)
        LoginViev(LoginViewModel(RepositoryUser)) {
            viewModel.login(it)
        }
    else {
        Text("AUTH!")
        // Пользователь авторизован
    }
}