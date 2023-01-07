package de.fejuma.impfi.ui.screen.start

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fejuma.impfi.data.RepositoryImpl
import de.fejuma.impfi.data.local.PrefSource
import kotlinx.coroutines.launch

class StartViewModel(private var repo:RepositoryImpl) : ViewModel() {
    // TODO: Implement the ViewModel


    private val _sfxVolume = mutableStateOf(getSfxVolume())
    public val sfxVolume: State<Int> get()  = _sfxVolume

    public fun setSfxVolume(sfxVolume:Int){
repo.setSfxVolume(sfxVolume)
        _sfxVolume.value = sfxVolume
    }

    init{
       //getSfxVolume()
        viewModelScope.launch {
            repo = RepositoryImpl(PrefSource(this.coroutineContext))
        }
    }

    private fun getSfxVolume()  = repo.getSfxVolume()



}