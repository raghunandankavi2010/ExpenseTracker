package me.raghu.expensetracker.utils

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData

/**
 *  A live data to watch for share preferences changes.
 */
@Suppress("UNUSED_ANONYMOUS_PARAMETER")
abstract class SharedPreferenceLiveData<T>(
    var sharedPrefs: SharedPreferences,
    var key: String,
    var defValue: T
) : LiveData<T>() {
    private val preferenceChangeListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (this@SharedPreferenceLiveData.key == key) {
                value = getValueFromPreferences(key, defValue)
            }
        }

    abstract fun getValueFromPreferences(key: String?, defValue: T): T
    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

    fun getStringLiveData(
        key: String?,
        defaultValue: String?
    ): SharedPreferenceLiveData<String> {
        return SharedPreferenceStringLiveData(sharedPrefs, key!!, defaultValue!!)
    }

}

