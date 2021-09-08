package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BigText(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier, color = Color.DarkGray, fontSize = 24.sp, fontWeight = FontWeight(700))
}

@Composable
fun NormalText(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight(400))
}