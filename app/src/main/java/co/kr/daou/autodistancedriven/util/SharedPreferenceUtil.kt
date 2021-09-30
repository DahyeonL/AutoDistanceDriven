package co.kr.daou.autodistancedriven.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setHeader(header: String) {
        val editor = preferences.edit()
        editor.putString(HEADER_NAME, header)
        editor.apply()
    }

    fun getHeader(): String? {
        return preferences.getString(HEADER_NAME,"")
    }

    companion object{
        const val SHARED_PREFERENCES_NAME = "auto_distance_driven_preference"
        const val HEADER_NAME = "alive_header"
    }
}