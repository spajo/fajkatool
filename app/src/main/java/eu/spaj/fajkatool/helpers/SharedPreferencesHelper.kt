package eu.spaj.fajkatool.helpers

import android.content.Context
import android.content.SharedPreferences
import eu.spaj.fajkatool.R

/**
 * elo Rafał on 30/07/2017.
 */
object SharedPreferencesHelper {

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    public fun <T> getProp(context: Context, property: String, defaultValue: T): T {
        val PREF_KEY = context.getString(R.string.prefs)
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        return when (defaultValue) {
            is Int -> sharedPreferences.getInt(property, defaultValue)
            is Boolean -> sharedPreferences.getBoolean(property, defaultValue)
            is String -> sharedPreferences.getString(property, defaultValue)
            is Float -> sharedPreferences.getFloat(property, defaultValue)
            is Long -> sharedPreferences.getLong(property, defaultValue)
            else -> defaultValue
        } as T
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    public fun <T> saveProp(context: Context, property: String, value: T) {
        val PREF_KEY = context.getString(R.string.prefs)
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        when (value) {
            is Int -> editor.putInt(property, value)
            is Boolean -> editor.putBoolean(property, value)
            is String -> editor.putString(property, value)
            is Float -> editor.putFloat(property, value)
            is Long -> editor.putLong(property, value)
            else -> return
        }
        editor.apply()
    }

}
