package de.fejuma.impfi.presentation.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentAboutBinding
import de.fejuma.impfi.ui.MinesweeperTheme


class AboutFragment : Fragment() {



    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.composeViewAbout.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MinesweeperTheme {

                    Column {

                        AboutItem(
                            stringResource(id = R.string.developers),
                            "Julian Alber, Felix Huber, Maximilian Wankmiller • Projekt an der DHBW Ravensburg",
                            painterResource(id = R.drawable.account_multiple_outline),
                        ) {
                            // This opens up a given URL in the phone's default browser
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.ravensburg.dhbw.de/")
                                )
                            )
                        }

                        AboutItem(
                            "Open Source-Lizenzen", "Automatisch generiert",
                            painterResource(id = R.drawable.book_open_outline),
                        ) {

                            // This opens up another activity. In this case the predefined Open
                            // Source Licenses
                            startActivity(
                                Intent(
                                    requireContext(),
                                    OssLicensesMenuActivity::class.java
                                )
                            )
                        }

                        AboutItem(
                            "Icons",
                            "materialdesignicons.com",
                            painterResource(id = R.drawable.sticker_emoji),
                        ) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://materialdesignicons.com/")
                                )
                            )
                        }

                        AboutItem(
                            "Sound Effekte",
                            "freesound.org",
                            painterResource(id = R.drawable.music_circle_outline),
                        ) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://freesound.org/")
                                )
                            )
                        }


                    }


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

// Whenever you notice that you do the same layout over and over again, but with different data, it
// is worth considering to transform it into its own components that you then call with the different data
@Composable
private fun AboutItem(
    headline: String,
    description: String,
    icon: Painter,
    onClick: () -> Unit
) = ListItem(
    headlineContent = {
        Text(
            headline,
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )
},
    modifier = Modifier.clickable {
        onClick()
    },
    leadingContent = {
        Icon(
            icon,
            contentDescription = null
        )
    },
    supportingContent = {
        Text(
            text = description,
            softWrap = true
        )
    }
)

@DefaultPreviews
@Composable
private fun AboutItemPreview() = MinesweeperTheme {

    AboutItem(
        headline = "Headline",
        description = "Description",
        icon = painterResource(id = R.drawable.music_circle_outline)
    ) {

    }
}