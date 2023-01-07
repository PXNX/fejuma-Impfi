package de.fejuma.impfi.data.local

import android.content.Context
import android.content.SharedPreferences
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel


class PrefSource(context:Context) {

    private val pref: SharedPreferences = context.getSharedPreferences("ImpfiPref", Context.MODE_PRIVATE)
    private val editor = pref.edit()

    public fun setSfxVolume(volume:Int) {
        SFX_VOLUME.put(volume)
    }
    public fun getSfxVolume() = SFX_VOLUME.getInt()

    public fun setDifficulty(difficultyLevel: DifficultyLevel) {
        DIFFICULTY.put(difficultyLevel.toString())
    }
    public fun getDifficulty() = DifficultyLevel.valueOf(DIFFICULTY.getString())


    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)
}