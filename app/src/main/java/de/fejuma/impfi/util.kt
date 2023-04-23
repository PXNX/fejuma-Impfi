package de.fejuma.impfi

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

internal fun formatTime(millis: Long): String {
    val secs = millis // / 1000
    return String.format("%02d%02d", secs % 3600 / 60, secs % 60)
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(locale = "de")
@Preview(fontScale = 1.5f)
annotation class DefaultPreviews