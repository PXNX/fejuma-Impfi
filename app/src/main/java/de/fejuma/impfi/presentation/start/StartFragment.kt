package de.fejuma.impfi.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentStartBinding
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.ui.component.DifficultyCard
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : Fragment() {


    private val viewModel by viewModels<StartViewModel>()


    private var _binding: FragmentStartBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.composeViewStart.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {


                MaterialTheme {


                    StartScreen(findNavController(), viewModel)


                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

@Composable
private fun SheetContent(viewModel: StartViewModel) {
    Text(
        "Einstellungen", modifier = Modifier.padding(16.dp),
        fontStyle = MaterialTheme.typography.h5.fontStyle,
        fontSize = MaterialTheme.typography.h5.fontSize,
        fontWeight = MaterialTheme.typography.h5.fontWeight
    )


    Text(
        "Schwierigkeit",
        modifier = Modifier.padding(horizontal = 16.dp),
        fontStyle = MaterialTheme.typography.h6.fontStyle,
        fontSize = MaterialTheme.typography.h6.fontSize
    )


    Row(
        Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        repeat(difficulties.size) {
            // Modifier.weight(1f,false)
            DifficultyCard(
                difficulties[it],
                viewModel.difficulty.value == difficulties[it].level
            ) { viewModel.setDifficulty(difficulties[it].level) }
        }


    }

    Text(
        "Effektlautstärke • ${viewModel.sfxVolume.value}%",
        modifier = Modifier.padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp
        ),
        fontStyle = MaterialTheme.typography.h2.fontStyle
    )




    Slider(
        value = viewModel.sfxVolume.value.toFloat(),
        onValueChange = {
            viewModel.setSfxVolume(it.toInt())
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        valueRange = 0f..100f,
        steps = 9
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun StartScreen(
    navController: NavController,
    viewModel: StartViewModel  //= hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { SheetContent(viewModel) }
    ) {
        // Screen content

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            Column(
                modifier = Modifier

                    .width(200.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.virus_outline),
                    contentDescription = ""
                )


                Button(onClick = {
                    navController.navigate(R.id.action_start_game)

                }, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painterResource(id = R.drawable.needle),
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Impfen")
                }

                Button(onClick = {
                    navController.navigate(R.id.action_start_scoreboard)
                }, modifier = Modifier.fillMaxWidth()) {


                    Icon(
                        painterResource(id = R.drawable.trophy_variant_outline),
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Highscores")
                }



                Button(onClick = {
                    scope.launch {
                        sheetState.show()
                    }


                }, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painterResource(id = R.drawable.cog_outline),
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Einstellungen")
                }

                Button(onClick = {
                    navController.navigate(R.id.action_start_about)

                }, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painterResource(id = R.drawable.information_outline),
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Über die App")
                }


            }
        }
    }
}