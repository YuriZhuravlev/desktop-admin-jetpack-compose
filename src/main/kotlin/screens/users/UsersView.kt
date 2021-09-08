package screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.UIUser

@Composable
fun UsersView(viewModel: UsersViewModel, modifier: Modifier = Modifier) {
    val users by viewModel.users.collectAsState()
    var selectUser by remember { mutableStateOf<UIUser?>(null) }
    if (users.data == null)
        viewModel.getUsers()

    Surface(modifier.fillMaxSize()) {
        val user = selectUser
        if (user == null) {
            LazyColumn(modifier.fillMaxWidth()) {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text("id", Modifier.width(20.dp), maxLines = 1)
                        Text("username", Modifier.weight(1f), maxLines = 1)
                        Text("is_blocked", Modifier.width(80.dp), maxLines = 1)
                        Text("strong_password", Modifier.width(130.dp), maxLines = 1)
                    }
                }
                item {
                    Box(Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))
                }
                items(users.data.orEmpty()) {
                    UserItem(it, Modifier.fillMaxWidth().clickable {
                        selectUser = it
                    })
                }
            }
        } else {
            UserItem(user, Modifier.fillMaxWidth().clickable {
                selectUser = null
            })
        }
    }
}

@Composable
fun UserItem(user: UIUser, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text("${user.id}", Modifier.width(20.dp), maxLines = 1)
        Text(user.username, Modifier.weight(1f), maxLines = 1)
        Checkbox(
            user.isBlocked,
            modifier = Modifier.width(80.dp).align(Alignment.CenterVertically),
            enabled = false,
            onCheckedChange = {})
        Checkbox(
            user.strongPassword,
            modifier = Modifier.width(130.dp).align(Alignment.CenterVertically),
            enabled = false,
            onCheckedChange = {})
    }
}