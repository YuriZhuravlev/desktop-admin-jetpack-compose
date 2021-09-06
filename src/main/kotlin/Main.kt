import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.Login

@Composable
@Preview
fun App() {
    DesktopMaterialTheme {
        Login()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
    title = "Журавлев А-05-18",
    icon = BitmapPainter(useResource("icon.png", ::loadImageBitmap))
    ) {
        App()
    }
}
