package de.fejuma.impfi

internal fun formatTime(millis: Long): String {
    val secs = millis / 1000
    return String.format("%02d:%02d", secs % 3600 / 60, secs % 60)
}