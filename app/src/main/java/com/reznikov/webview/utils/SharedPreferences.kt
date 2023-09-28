package com.reznikov.webview.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    companion object {
        const val PREFS_NAME = "my_prefs"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: String) {
        sharedPref.let {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(KEY_NAME, value)
            editor.apply()
        }
    }

    fun getValue(KEY_NAME: String): String?{
        sharedPref.let { return sharedPref.getString(KEY_NAME, null) }
    }

    fun contains(KEY_NAME: String): Boolean {
        sharedPref.let {
            return sharedPref.contains(KEY_NAME)
        }
    }
}