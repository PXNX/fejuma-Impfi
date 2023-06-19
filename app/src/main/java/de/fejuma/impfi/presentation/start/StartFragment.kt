package de.fejuma.impfi.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import de.fejuma.impfi.presentation.start.component.PreferenceSheetContent
import de.fejuma.impfi.ui.MinesweeperTheme


@AndroidEntryPoint
class StartFragment : Fragment() {

    private val viewModel by viewModels<StartViewModel>()

    private var _binding: FragmentStartBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

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
                MinesweeperTheme {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartScreen(
    navController: NavController,
    viewModel: StartViewModel  //= hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }


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
                painterResource(id = R.mipmap.applogo),
                contentDescription = "Virus",
                Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))


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
                openBottomSheet = true
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

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = sheetState,
        ) {
            PreferenceSheetContent(viewModel = viewModel)
        }
    }
}