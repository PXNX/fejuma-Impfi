package de.fejuma.impfi.presentation.game.component.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
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
            Text(text = stringResource(id = R.string.game_over))
        },

        confirmButton = {
            Button(
                {
                    onConfirm()
                }
            ) {
                Text(stringResource(id = R.string.continue_button))
            }
        },

        )
}


@DefaultPreviews
@Composable
fun GameLostDialogPreview() = MinesweeperTheme {
    GameLostDialog {}
}

