package de.fejuma.impfi.presentation.game.component.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.formatTime
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.ui.MinesweeperTheme
import de.fejuma.impfi.ui.lightGray


@Composable
fun GameWonDialog(
    timePlayed: Int,
    difficulty: DifficultyLevel,
    hintsUsed: Int,
    onConfirm: (Highscore) -> Unit,
    onDismiss: () -> Unit
) {


    var (userName, setUserName) = remember {
        androidx.compose.runtime.mutableStateOf(
            ""
        )
    }
    val timeFormat = formatTime(timePlayed)


//if verzweigung, welches jeweil unterschiedliche Dialogbox öffnet? (Gewonnen und neuer HighScore || Gewonnen aber kein neuer HS || verloren
//TODO: Fix focus and layout (Material Compose TextField Validation...)


    AlertDialog(
        onDismissRequest = {

        }, icon = {
            Icon(
                painterResource(id = R.drawable.alarm),
                contentDescription = "",
                tint = lightGray
            )
        },
        title = {
            // three states: won, lost, new highsscroe?
            Text(text = "Game Won!")
        },
        text = {

            Column {


                //todo: mit icon daneben aufpeppen, gewinnen muss sich geil anfühlen!
                Text(
                    "Benötigte Zeit: $timeFormat",
                    Modifier.padding(start = 10.dp)
                )

                Text(
                    "Benutzte Hinweise: $hintsUsed",
                    Modifier.padding(start = 10.dp)
                )

                TextField(value = userName, onValueChange = {
                    setUserName(it)
                }, modifier = Modifier.padding(vertical = 8.dp),
                    label = { Text(text = "Username") },
                    singleLine = true,
                    isError = userName.isBlank(),
                    supportingText = { if (userName.isBlank()) Text(text = "Bitte Username angeben") })
            }
        },
        confirmButton = {
            Button(
                {


                  onConfirm(Highscore(userName, difficulty, timePlayed, hintsUsed))

                }
            ) {
                Text("Score speichern")
            }
        },
        dismissButton = {
            Button(
                { onDismiss() })
            {
                Text("Abbrechen")
            }
        }
    )
}

@DefaultPreviews
@Composable
fun GameWonDialogPreview() = MinesweeperTheme {

    GameWonDialog(69, DifficultyLevel.EASY, 1, {}, {})

}