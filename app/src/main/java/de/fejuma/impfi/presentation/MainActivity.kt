package de.fejuma.impfi.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R

// Entry into the App, the layout contains the container for our fragments. Jetpack Navigation
// handles the rest. See the nav_graph.xml for definitions of routes.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}