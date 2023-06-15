package de.fejuma.impfi.presentation.game.component.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.ui.MinesweeperTheme

@Composable
fun GameLostDialog(

    onConfirm: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = {
            onConfirm()
        },
        title = {
            // three states: won, lost, new highsscroe?
            //todo: some icon?
            Text(text = "GAME OVER")
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

        )
}


@DefaultPreviews
@Composable
fun GameLostDialogPreview() = MinesweeperTheme {
    GameLostDialog {}
}

