package de.fejuma.impfi.presentation.start.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.ui.MinesweeperTheme

@Composable
fun NavigationButton(onClick: () -> Unit, icon: Painter, text: String) {

    Button(onClick = {
        onClick()
    }, modifier = Modifier.fillMaxWidth()) {
        Icon(
            icon,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text)
    }
}

@DefaultPreviews
@Composable
private fun GameWonDialogPreview() = MinesweeperTheme {
    NavigationButton(onClick = { }, icon = painterResource(id = R.drawable.help), text = "Text")
}