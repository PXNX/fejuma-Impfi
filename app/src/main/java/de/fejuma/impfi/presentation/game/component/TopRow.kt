package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.formatTime
import de.fejuma.impfi.presentation.game.GameViewModel
import de.fejuma.impfi.ui.MinesweeperTheme
import de.fejuma.impfi.ui.lightGray

@Composable
fun TopRow(
    viewModel: GameViewModel,
    time: Int,
    mines: Int,
    openDialog: (Boolean) -> Unit
) {
//todo: using Units (state hoisting) is smoother than passing down state - also when using scope?


    val scope = rememberCoroutineScope()

    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Row {
                Icon(
                    painterResource(id = R.drawable.alarm),
                    contentDescription = "",
                    tint = lightGray
                )


                val timeFormat = formatTime(time.toLong())

                AnimatingCharacter(
                    modifier = Modifier.padding(start = 10.dp),
                    character = timeFormat[0]
                )

                AnimatingCharacter(character = timeFormat[1])

                Text(
                    text = ":",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )


                AnimatingCharacter(character = timeFormat[2])
                AnimatingCharacter(character = timeFormat[3])


            }

            Spacer(modifier = Modifier.height(6.dp))



            Row {
                Icon(
                    painterResource(id = R.drawable.virus_outline),
                    contentDescription = "",
                    tint = lightGray
                )

                //fixme: ugly af
                mines.toString().forEachIndexed { index, c ->
                    if (index == 0)
                        AnimatingCharacter(

                            modifier = Modifier.padding(start = 10.dp),
                            character = if (mines.toString().length > 1) '0' else mines.toString()[0]
                        ) else

                        AnimatingCharacter(
                            character = mines.toString()[index]
                        )

                }


            }
        }

        Button(onClick = {
            openDialog(true)

        }) {

            Icon(
                painterResource(id = R.drawable.grave_stone),
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Aufgeben")
        }


    }

}

@DefaultPreviews
@Composable
fun TopRowPreview() = MinesweeperTheme {
    TopRow(viewModel = GameViewModel(RepositoryMock), 120, 15) {}
}

