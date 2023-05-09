package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.R
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.presentation.game.GameViewModel
import de.fejuma.impfi.ui.lightGray

@Composable
fun GameEndDialog(
    has_won: Boolean,
    onConfirm: () -> Unit,
    onDismiss: (Boolean) -> Unit,
    viewModel: GameViewModel
) {

    var (userName, setUserName) = remember { mutableStateOf("") }
    //if verzweigung, welches jeweil unterschiedliche Dialogbox öffnet? (Gewonnen und neuer HighScore || Gewonnen aber kein neuer HS || verloren
//TODO: Fix focus and layout (Material Compose TextField Validation...)
    AlertDialog(
        onDismissRequest = {

        }, icon = {Icon(
            painterResource(id = R.drawable.alarm),
            contentDescription = "",
            tint = lightGray
        )},
        title = {
            // three states: won, lost, new highsscroe?
            Text(text = "Dialog Title")
        },
        text = {

            Column {


            Text("Benötigte Zeit 4:20", Modifier.padding(start = 10.dp))

            TextField(value = userName, onValueChange = {
                setUserName(it)
            }, modifier = Modifier.padding(vertical = 8.dp),
                label = { Text(text = "Username")},
                singleLine = true,
                isError = userName.isBlank(),
                supportingText = { if(userName.isBlank())Text(text = "Bitte Username angeben")})
            }
        },
        confirmButton = {
            Button(
                {
                    onConfirm()
                }
            ) {
                Text("Weiter")
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    onDismiss(false)
                   // viewModel.newGame()
                }) {
                Text("This is the dismiss Button")
            }
        }
    )


}

@Preview
@Composable
fun GameEndDialogPreview() {
    GameEndDialog(
        has_won = true,
        onConfirm = {},
        onDismiss = {},
        viewModel = GameViewModel(RepositoryMock)
    )
}

