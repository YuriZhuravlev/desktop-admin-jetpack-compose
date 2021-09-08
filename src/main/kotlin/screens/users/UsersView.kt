package screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import data.model.UIUser
import screens.main.isAdmin
import ui.NormalText

@Composable
fun UsersView(viewModel: UsersViewModel, modifier: Modifier = Modifier) {
    val users by viewModel.users.collectAsState()
    val selectedUser by viewModel.selectedUser.collectAsState()
    if (users.data == null)
        viewModel.getUsers()

    Surface(modifier.fillMaxSize()) {
        val user = selectedUser
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
                        viewModel.selectUser(it)
                    })
                }
            }
        } else {
            UserDetails(viewModel, Modifier.fillMaxWidth())
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

@Composable
fun UserDetails(viewModel: UsersViewModel, modifier: Modifier) {
    val selectedUser by viewModel.selectedUser.collectAsState()
    Column(modifier) {
        Icon(
            painterResource("ic_close.svg"),
            "",
            Modifier.align(Alignment.End).clickable {
                viewModel.getUsers()
                viewModel.selectUser(null)
            }.padding(8.dp)
        )
        val user = selectedUser
        if (user != null) {
            NormalText("id: ${user.id}")
            Divider(Modifier.fillMaxWidth())
            NormalText("username: ${user.username}")
            Divider(Modifier.fillMaxWidth())
            Row {
                NormalText("is_blocked: ")
                Switch(user.isBlocked, onCheckedChange = {
                    viewModel.patchIsBlocked(it)
                }, enabled = !user.isAdmin)
            }
            Divider(Modifier.fillMaxWidth())
            Row {
                NormalText("strong_password: ")
                Switch(user.strongPassword, onCheckedChange = {
                    viewModel.patchStrongPassword(it)
                })
            }
        }
        if ((viewModel.users.value.data?.size ?: 0) > 1) {
            Row(Modifier.fillMaxSize().padding(bottom = 8.dp, top = 24.dp)) {
                Icon(painterResource("ic_back.svg"), "", Modifier.clickable {
                    viewModel.backUser()
                })
                Box(Modifier.weight(1f))
                Icon(painterResource("ic_next.svg"), "", Modifier.clickable {
                    viewModel.nextUser()
                })
            }
        }
    }
}