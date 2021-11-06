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
import screens.main.MainView
import screens.main.MainViewModel
import screens.session.SessionView
import screens.session.SessionViewModel
import kotlin.system.exitProcess

@Composable
@Preview
fun App() {
    DesktopMaterialTheme {
        var sessionState by remember { mutableStateOf(SessionState.LOGIN_SESSION) }
        when (sessionState) {
            SessionState.LOGIN_SESSION -> SessionView(SessionViewModel()) { sessionState = it }
            SessionState.SESSION -> MainView(MainViewModel(RepositoryUser))
            SessionState.CLOSE_SESSION -> exitProcess(0)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = {
            // TODO шифрование
            exitApplication()
        },
        title = "Журавлев А-05-18",
        icon = BitmapPainter(useResource("icon.png", ::loadImageBitmap))
    ) {
        App()
    }
}
