package de.fejuma.impfi.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    @OptIn(ExperimentalMaterialApi::class)
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

                MaterialTheme() {


                    LazyColumn {
                        item {

                            ListItem(icon = {
                                Icon(
                                    painterResource(id = R.drawable.account_multiple_outline),
                                    contentDescription = ""
                                )
                            }, secondaryText = {
                                Text(
                                    text = "Julian Alber, Felix Huber, Maximilian Wankmiller",
                                    softWrap = true
                                )
                            }) {

                                Text(
                                    "Entwickler",
                                    fontStyle = MaterialTheme.typography.h6.fontStyle,
                                    fontSize = MaterialTheme.typography.h6.fontSize
                                )
                            }


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