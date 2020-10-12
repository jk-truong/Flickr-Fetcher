package com.example.photogallery

import android.content.Context
import android.provider.Settings.Secure.putString
import androidx.preference.PreferenceManager

private const val PREF_SEARCH_QUERY = "searchQuery"

//Use object because this is a singleton. We only need one instance.
object QueryPreferences {

    fun getStoredQuery(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_SEARCH_QUERY, "")!!     //we know this is never null so use !!
    }

    fun setStoredQuery(context: Context, query: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit ()
            .putString(PREF_SEARCH_QUERY, query)
            .apply()
    }
}