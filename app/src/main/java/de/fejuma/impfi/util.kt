package de.fejuma.impfi

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


internal fun formatTime(secs: Int): String = String.format("%02d:%02d", secs % 3600 / 60, secs % 60)

internal fun formatNumber(amount: Int): String = String.format("%02d", amount)


// Creating additional previews with different settings, so that we can preview all of them at once.
@Preview(name = "background", showBackground = true)
@Preview(name = "darkmode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "german", locale = "de")
@Preview(name = "enlarged", fontScale = 1.5f)
annotation class DefaultPreviews