import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.model.SessionState
import data.repository.RepositoryUser
import data.repository.encrypt
import screens.close_session.CloseSession
import screens.main.MainView
import screens.main.MainViewModel
import screens.session.SessionView
import screens.session.SessionViewModel
import java.security.Key

private var key: Key? = null

@Composable
@Preview
fun App() {
    DesktopMaterialTheme {
        var sessionState by remember { mutableStateOf(SessionState.LOGIN_SESSION) }
        when (sessionState) {
            SessionState.LOGIN_SESSION -> SessionView(SessionViewModel()) { state, pass ->
                sessionState = state
                if (state == SessionState.SESSION) {
                    key = pass
                }
            }
            SessionState.SESSION -> MainView(MainViewModel(RepositoryUser))
            SessionState.CLOSE_SESSION -> CloseSession()
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = {
            key?.let { key ->
                encrypt(key)
            }
            exitApplication()
        },
        title = "Журавлев А-05-18",
        icon = BitmapPainter(useResource("icon.png", ::loadImageBitmap))
    ) {
        App()
    }
}
