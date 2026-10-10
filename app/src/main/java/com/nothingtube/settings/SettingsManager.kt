package com.nothingtube.settings

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object SettingsManager {
    private const val PREFS = "nothing_tube"
    private const val KEY_API = "api_key"
    private const val KEY_ACCENT = "accent_color"
    private const val KEY_DOTS = "show_dots"
    private const val KEY_COMPACT = "compact_mode"
    private const val KEY_MUSIC_DEFAULT = "music_default"

    var apiKey by mutableStateOf("")
    var accentColor by mutableStateOf("red")
    var showDots by mutableStateOf(true)
    var compactMode by mutableStateOf(false)
    var musicDefault by mutableStateOf(false)

    fun load(context: Context) {
        val p = context.getSharedPreferences(PREFS, 0)
        apiKey = p.getString(KEY_API, "") ?: ""
        accentColor = p.getString(KEY_ACCENT, "red") ?: "red"
        showDots = p.getBoolean(KEY_DOTS, true)
        compactMode = p.getBoolean(KEY_COMPACT, false)
        musicDefault = p.getBoolean(KEY_MUSIC_DEFAULT, false)
    }

    fun saveApiKey(context: Context, value: String) {
        apiKey = value
        context.getSharedPreferences(PREFS, 0).edit().putString(KEY_API, value).apply()
    }

    fun setAccent(context: Context, value: String) {
        accentColor = value
        context.getSharedPreferences(PREFS, 0).edit().putString(KEY_ACCENT, value).apply()
    }

    fun setShowDots(context: Context, value: Boolean) {
        showDots = value
        context.getSharedPreferences(PREFS, 0).edit().putBoolean(KEY_DOTS, value).apply()
    }

    fun setCompactMode(context: Context, value: Boolean) {
        compactMode = value
        context.getSharedPreferences(PREFS, 0).edit().putBoolean(KEY_COMPACT, value).apply()
    }

    fun setMusicDefault(context: Context, value: Boolean) {
        musicDefault = value
        context.getSharedPreferences(PREFS, 0).edit().putBoolean(KEY_MUSIC_DEFAULT, value).apply()
    }
}
