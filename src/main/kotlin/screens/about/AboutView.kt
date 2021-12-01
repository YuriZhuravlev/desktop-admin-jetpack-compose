package screens.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.BigText
import ui.NormalText

@Composable
fun AboutView(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Column(modifier) {
        Icon(
            painterResource("ic_close.svg"),
            "",
            Modifier.align(Alignment.End).clickable {
                onClose()
            }.padding(8.dp)
        )
        BigText("Журавлев Юрий А-05-18", Modifier.padding(8.dp))
        BigText("Лабораторная работа №6", Modifier.align(Alignment.CenterHorizontally).padding(bottom = 4.dp))
        NormalText("Защита данных", Modifier.align(Alignment.CenterHorizontally))
        NormalText("Индивидуальные варианты заданий (ограничения на выбираемые пароли)", Modifier.padding(8.dp))
        NormalText("8. Наличие латинских букв и символов кириллицы.", Modifier.padding(horizontal = 8.dp))
        NormalText("Шифрование гаммированием")
        NormalText(
            "Собирается информация о компьютере:\n" +
                    " * имя пользователя;\n" +
                    " * имя компьютера;\n" +
                    " * путь к папке с ОС Windows;\n" +
                    " * путь к папке с системными файлами ОС Windows;\n" +
                    " * ширина экрана;\n" +
                    " * набор дисковых устройств;\n" +
                    " * объем диска, на котором установлена программа.\n"
        )
    }
}