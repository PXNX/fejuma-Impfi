package de.fejuma.impfi.presentation.game

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    //instead of using lots of separate states, it may be better to go for dataclass -- less bloat

}