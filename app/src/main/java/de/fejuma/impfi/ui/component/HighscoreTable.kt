package de.fejuma.impfi.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import de.fejuma.impfi.R

@Composable
fun HighscoreTable (difficultyName: String) {

    Column() {
        Text(text = "Highscore • $difficultyName")

        Row() {
            Icon(painterResource(id = R.drawable.account_multiple_outline), contentDescription = stringResource(
                id = R.string.highscore_user
            ));

            Icon(painterResource(id = R.drawable.alarm), contentDescription = stringResource(
                id = R.string.highscore_time
            ))
        }

        Row() {
            Text(text = "Rainer Zufall")
            Text(text = "10:24")
        }
    }


}