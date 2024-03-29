package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.formatNumber
import de.fejuma.impfi.ui.MinesweeperTheme
import de.fejuma.impfi.ui.lightGray


@Composable
fun TopRow(
    time: @Composable () -> Unit,
    mines: Int,
    openDialog: (Boolean) -> Unit,
    showHint: () -> Unit,
    canShowHint: Boolean
) {


    // use Box to place items on top of each other
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {

        Row {
            FilledIconButton(onClick = {
                openDialog(true)
            }) {
                Icon(
                    painterResource(id = R.drawable.grave_stone),
                    contentDescription = "",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }

            FilledIconButton(onClick = {
                showHint()
            }, enabled = canShowHint) {
                Icon(
                    painterResource(id = R.drawable.help),
                    contentDescription = "",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )

            }
        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painterResource(id = R.drawable.alarm),
                contentDescription = "",
                tint = lightGray
            )


            Column(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                time()
            }

            // To give enough room for the two icon buttons in the center
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(id = R.drawable.virus_outline),
                contentDescription = "",
                tint = lightGray
            )

            formatNumber(mines).forEach {
                AnimatingCharacter(it)
            }
        }
    }
}

@DefaultPreviews
@Composable
fun TopRowPreview() = MinesweeperTheme {
    Column {
        TopRow({}, 15, {}, {}, true)
        TopRow({}, 15, {}, {}, false)

    }
}

