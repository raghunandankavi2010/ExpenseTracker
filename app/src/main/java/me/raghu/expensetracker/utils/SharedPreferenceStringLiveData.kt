package me.raghu.expensetracker.utils

import android.content.SharedPreferences

/**
 *  A live data to listen to string preference changes.
 */

class SharedPreferenceStringLiveData(
    sharedPrefs: SharedPreferences,
    key: String,
    defValue: String
) :
    SharedPreferenceLiveData<String>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String?, defValue: String): String {
        return sharedPrefs.getString(key, defValue)!!
    }
}