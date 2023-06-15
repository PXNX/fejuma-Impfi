package de.fejuma.impfi.feedback

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Immutable
import de.fejuma.impfi.R

@Immutable
class AudioManager(
    private val context: Context,
    private val sfxVolume: Int,
) {

    private val tlc: TrackedLazyCollector<MediaPlayer> by lazy { TrackedLazyCollector() }

    // region : MediaPlayers
    private val sfxPop: MediaPlayer by mediaPlayerOf(R.raw.pop) {
        setVolume(0.1f, 0.1f)
    }
    private val sfxAffirmative: MediaPlayer by mediaPlayerOf(R.raw.affirmative) {
        setVolume(0.1f, 0.1f)
    }
    private val sfxCancel: MediaPlayer by mediaPlayerOf(R.raw.cancel) {
        setVolume(0.1f, 0.1f)
    }
    private val sfxSuccess: MediaPlayer by mediaPlayerOf(R.raw.success) {
        setVolume(0.1f, 0.1f)
    }

    private val sfxFailure: MediaPlayer by mediaPlayerOf(R.raw.failure) {
        setVolume(0.1f, 0.1f)
    }
    // endregion

    // region : Public API
    fun pop() = sfxPop.play()
    fun affirmative() = sfxAffirmative.play()
    fun cancel() = sfxCancel.play()
    fun success() = sfxSuccess.play()
    fun failure() = sfxFailure.play()

    fun dispose() {
        tlc.onEachInitialized { player ->
            player.stop()
            player.release()
        }
    }
    // endregion

    // region : Helper methods

    private fun MediaPlayer.play() {
        if (sfxVolume == 0) return
        this.start()
    }

    private val MediaPlayer.isPaused: Boolean
        get() = !isPlaying && currentPosition > 1

    private fun mediaPlayerOf(
        resId: Int,
        configuration: MediaPlayer.() -> Unit = {},
    ): Lazy<MediaPlayer> = tlc.trackedLazy {
        MediaPlayer
            .create(context, resId)
            .apply { configuration() }
    }
    // endregion
}

internal class TrackedLazyCollector<T> {

    private val delegates: MutableList<Lazy<T>> = mutableListOf()

    fun trackedLazy(logic: () -> T): Lazy<T> {
        val delegate = lazy { logic() }
        synchronized(delegates) { delegates.add(delegate) }
        return delegate
    }

    fun onEachInitialized(logic: (T) -> Unit) {

        synchronized(delegates) {
            delegates.forEach { if (it.isInitialized()) logic(it.value) }
        }
    }
}