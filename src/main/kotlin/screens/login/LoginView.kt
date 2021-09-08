package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import data.Resource
import org.jetbrains.skija.impl.Log
import screens.login.LoginViewModel

@Composable
fun LoginViev(viewModel: LoginViewModel) {
    val user by viewModel.user.collectAsState()

    Column {
        when (user?.status) {
            Resource.Status.SUCCESS -> {
                Text("success")
            }
            Resource.Status.LOADING -> {
                Text("loading")
            }
            Resource.Status.ERROR -> {
                Text("error: ${user?.error?.message}")
                Log.debug(user?.error?.stackTrace?.joinToString("\n"))
            }
            else -> {
                Text("null")
            }
        }

        Button(onClick = {
            viewModel.getUser("ADMIN")
        }) {
            Text("btn")
        }
    }
}