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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentStartBinding
import de.fejuma.impfi.presentation.start.component.NavigationButton
import de.fejuma.impfi.presentation.start.component.PreferenceSheetContent
import de.fejuma.impfi.ui.MinesweeperTheme


@AndroidEntryPoint
class StartFragment : Fragment() {

    private val viewModel by viewModels<StartViewModel>()

    private var _binding: FragmentStartBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    //provides access to the views in the layout
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
                    // Displaying the StartScreen composable with the provided NavController and viewModel
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
    viewModel: StartViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    // Saveable will keep the state, even if we were to change something with our device, e.g. the
    // darkmode or screen orientation
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

            // Displaying the app logo image
            Image(
                painterResource(id = R.mipmap.applogo),
                contentDescription = "Virus",
                Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))


            // Button to navigate to the game screen
            NavigationButton(
                onClick = {
                    navController.navigate(R.id.action_start_game)
                },
                painterResource(id = R.drawable.needle),
                stringResource(id = R.string.vaccinate)
            )


            // Button to navigate to the scoreboard screen
            NavigationButton(
                onClick = {
                    navController.navigate(R.id.action_start_scoreboard)
                }, painterResource(id = R.drawable.trophy_variant_outline),
                "Highscores"
            )

            // Button to open the preference settings bottom sheet
            NavigationButton(
                onClick = {
                    openBottomSheet = true
                },
                painterResource(id = R.drawable.cog_outline),
                stringResource(id = R.string.settings)
            )

            // Button to navigate to the about screen
            NavigationButton(
                onClick = {
                    navController.navigate(R.id.action_start_about)
                }, painterResource(id = R.drawable.information_outline),
                stringResource(id = R.string.about)
            )
        }


        // As this state changes, it may display the bottom sheet
        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = sheetState,
            ) {
                PreferenceSheetContent(viewModel = viewModel)
            }
        }
    }
}