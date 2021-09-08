package screens.add_user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.UIUser
import ui.NormalText

@Composable
fun AddUserView(viewModel: AddUserViewModel, modifier: Modifier = Modifier, onSuccess: (UIUser) -> Unit) {
    Column(modifier) {
        val result by viewModel.result.collectAsState()
        var name by remember { mutableStateOf("") }
        var strongPassword by remember { mutableStateOf(false) }
        if (result.status.isSuccess())
            result.data?.let(onSuccess)
        NormalText("Введите имя пользователя", Modifier.padding(top = 16.dp))
        TextField(name, onValueChange = { name = it }, Modifier.padding(vertical = 8.dp))
        Row(Modifier.padding(bottom = 8.dp)) {
            NormalText("Сложный пароль: ")
            Switch(strongPassword, onCheckedChange = {
                strongPassword = it
            })
        }
        if (result.status.isError()) {
            Text("Имя занято", color = Color.Red, modifier = Modifier.padding(bottom = 4.dp))
        }
        Button(onClick = {
            viewModel.addUser(name, strongPassword)
        }, enabled = name.isNotBlank()) {
            Text("Добавить")
        }
    }

}