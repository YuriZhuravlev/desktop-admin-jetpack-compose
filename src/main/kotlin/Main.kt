import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.repository.RepositoryUser
import screens.main.MainView
import screens.main.MainViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainView(MainViewModel(RepositoryUser))
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
