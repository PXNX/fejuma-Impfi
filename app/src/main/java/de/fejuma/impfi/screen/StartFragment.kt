package de.fejuma.impfi.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    // TODO: Rename and change types of parameters


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
        binding.btStart.apply {
            setOnClickListener {
                findNavController().navigate(R.id.route_start_game)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}