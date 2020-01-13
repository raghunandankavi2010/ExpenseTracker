package me.raghu.expensetracker.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.takisoft.preferencex.EditTextPreference
import com.takisoft.preferencex.PreferenceFragmentCompat
import me.raghu.expensetracker.R


@Suppress("UNUSED_VARIABLE")
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            val etPref: EditTextPreference? =
                findPreference("income_monthly") as EditTextPreference?
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
           // setPreferencesFromResource(R.xml.preferences, rootKey)

        }
    }
}