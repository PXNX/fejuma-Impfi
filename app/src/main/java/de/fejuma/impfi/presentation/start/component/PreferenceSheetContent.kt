package de.fejuma.impfi.presentation.start.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.presentation.start.StartViewModel
import de.fejuma.impfi.ui.MinesweeperTheme


@Composable
fun PreferenceSheetContent(viewModel: StartViewModel) {


    Text(
        "Schwierigkeit",
        Modifier
            .padding(horizontal = 16.dp),
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(16.dp))


    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        difficulties.forEach { (level, difficulty) ->
            // Modifier.weight(1f,false)
            DifficultyCard(
                difficulty,
                viewModel.difficulty == level
            ) { viewModel.changeDifficulty(level) }
        }


    }

    Spacer(modifier = Modifier.height(32.dp))

    Text(
        "Effektlautstärke • ${viewModel.sfxVolume.toInt()}%",
        Modifier
            .padding(horizontal = 16.dp),
        style = MaterialTheme.typography.titleMedium
    )

    Slider(
        value = viewModel.sfxVolume,
        onValueChange = viewModel::changeSfxVolume,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        valueRange = 0f..100f,
        steps = 9
    )

    Spacer(modifier = Modifier.height(22.dp))

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Haptisches Feedback",

            style = MaterialTheme.typography.titleMedium
        )

        Switch(
            checked = viewModel.hapticsEnabled,
            onCheckedChange =
            viewModel::changeHapticEnabled
        )
    }

    Spacer(modifier = Modifier.height(64.dp))


}

@DefaultPreviews
@Composable
private fun PreferenceSheetContentPreview() = MinesweeperTheme {
    Column {
        PreferenceSheetContent(viewModel = StartViewModel(RepositoryMock))
    }
}