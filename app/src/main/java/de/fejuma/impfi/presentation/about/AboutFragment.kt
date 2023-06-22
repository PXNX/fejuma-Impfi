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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentAboutBinding
import de.fejuma.impfi.ui.MinesweeperTheme


class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters


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
                        ListItem(headlineContent = {
                            Text(
                                stringResource(id = R.string.developers),
                                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        }, leadingContent = {
                            Icon(
                                painterResource(id = R.drawable.account_multiple_outline),
                                contentDescription = ""
                            )
                        }, supportingContent = {
                            Text(
                                text = "Julian Alber, Felix Huber, Maximilian Wankmiller",
                                softWrap = true
                            )
                        })

                        ListItem(headlineContent = {
                            Text(
                                "Open Source-Licences",
                                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        },
                            modifier = Modifier.clickable {

                                startActivity(
                                    Intent(
                                        requireContext(),
                                        OssLicensesMenuActivity::class.java
                                    )
                                )
                            },
                            leadingContent = {
                                Icon(
                                    painterResource(id = R.drawable.book_open_outline),
                                    contentDescription = ""
                                )
                            })


                        ListItem(headlineContent = {
                            Text(
                                "Icons",
                                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        },
                            modifier = Modifier.clickable {

                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://materialdesignicons.com/")
                                    )
                                )
                            },
                            leadingContent = {
                                Icon(
                                    painterResource(id = R.drawable.sticker_emoji),
                                    contentDescription = ""
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = "materialdesignicons.com",
                                    softWrap = true
                                )
                            }

                        )

                        ListItem(headlineContent = {
                            Text(
                                "Sound Effekte",
                                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        },
                            modifier = Modifier.clickable {


                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://freesound.org/")
                                    )
                                )
                            },
                            leadingContent = {
                                Icon(
                                    painterResource(id = R.drawable.music_circle_outline),
                                    contentDescription = ""
                                )
                            },
                            supportingContent = {

                                Text(
                                    text = "freesound.org"
                                )

                            }

                        )
//Todo: sollen hier alle Interpreten der Soundeffekte einzeln genannt werden?

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