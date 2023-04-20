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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.R
import de.fejuma.impfi.presentation.game.GameViewModel
import de.fejuma.impfi.ui.lightGray

@Composable
fun TopRow(
    viewModel: GameViewModel,
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
                Text("4:20", Modifier.padding(start = 10.dp))
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row {
                Icon(
                    painterResource(id = R.drawable.virus_outline),
                    contentDescription = "",

                    )

                Text("10", Modifier.padding(start = 10.dp))
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