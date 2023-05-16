package de.fejuma.impfi.presentation.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
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


                    LazyColumn {
                        item {

                            ListItem(headlineContent = {
                                Text(
                                    "Entwickler",
                                    fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
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


//Todo: Github, Icons, Lizenzen

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