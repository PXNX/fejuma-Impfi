package de.fejuma.impfi.feedback


import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Immutable

@Immutable
class HapticManager(
    private val context: Context,
    private val isHapticFeedbackAllowed: Boolean,
) {


    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager: VibratorManager =
                (context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager)
            vibratorManager.defaultVibrator
        } else {
            // backward compatibility for Android API < 31,
            // VibratorManager was only added on API level 31 release.
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun pop() = vibrationNow(mills = 2, amplitude = 100)
    fun shortVibrationNow() = vibrationNow(mills = 200, amplitude = 100)
    fun mediumVibrationNow() = vibrationNow(mills = 500, amplitude = 100)

    private fun vibrationNow(
        mills: Long,
        amplitude: Int,
    ) {

        if (!isHapticFeedbackAllowed) return

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(mills, amplitude))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(mills)
        }
    }
}