package de.fejuma.impfi.presentation.game.component.dialogs

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
import androidx.compose.ui.res.stringResource
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
    // This requires a function that returns Highscore
    onConfirm: (Highscore) -> Unit,
    onDismiss: () -> Unit
) {

    // Destructuring it into getter and setter may be useful. Instead for setting a value, you can
    // also call userName.value = yourValue
    val (userName, setUserName) = remember {
        mutableStateOf(
            ""
        )
    }
    val timeFormat = formatTime(timePlayed)


    AlertDialog(
        onDismissRequest = {
            // We don't want anything to happen when the user clicks outside of the dialog
        }, icon = {
            Icon(
                painterResource(id = R.drawable.alarm),
                contentDescription = "",
                tint = lightGray
            )
        },
        title = {
            Text(text = stringResource(id = R.string.game_won))
        },
        text = {

            Column {


                Text(
                    stringResource(id = R.string.time_needed, ": $timeFormat"),
                    Modifier.padding(start = 10.dp)
                )

                Text(
                    stringResource(id = R.string.used_hints, ": $hintsUsed"),
                    Modifier.padding(start = 10.dp)
                )

                TextField(value = userName, onValueChange = {
                    setUserName(it)
                }, modifier = Modifier.padding(vertical = 8.dp),
                    label = { Text(text = "Username") },
                    singleLine = true,
                    isError = userName.isBlank(),
                    supportingText = { if (userName.isBlank()) Text(text = stringResource(id = R.string.support_text)) })
            }
        },
        confirmButton = {
            Button(
                {
                    // As noted above: We have the return a Highscore when calling onConfirm
                    onConfirm(Highscore(userName, difficulty, timePlayed, hintsUsed))
                }
            ) {
                Text(stringResource(id = R.string.save_button))
            }
        },
        dismissButton = {
            Button(
                { onDismiss() })
            {
                Text(stringResource(id = R.string.dismiss_button))
            }
        }
    )
}

@DefaultPreviews
@Composable
private fun GameWonDialogPreview() = MinesweeperTheme {
    GameWonDialog(69, DifficultyLevel.EASY, 1, {}, {})
}