package com.islam.amaan.tracknamaaz.ui.activities.timingsactivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.islam.amaan.tracknamaaz.R
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.ctx

/**
 * The Class Activity used to check for Runtime App Permissions
 */
class TimingsSettingsActivity : AppCompatActivity() {

    /**
     * array of app permissions to be checked for the Location Services
     */
    val permissions = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timings_settings)
        setSupportActionBar(appToolbar)
        if (Build.VERSION.SDK_INT >= 23) checkPermissions()
    }

    /**
     * Method used to check the Permissions needed to grab the Location in the custom
     * Preference Settings
     * @return boolean result (FUTURE PROOFING :D)
     */
    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(arrayOfNulls<String>(listPermissionsNeeded.size)),
                    100)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //code to be implemented later
                //disable the Location Preference entirely and load up a Edit Text Preference.
            }
            return
        }
    }

}


class TimingsPreferenceFragment : PreferenceFragment(), Preference.OnPreferenceChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.timings_preferences)

        val location = findPreference(getString(R.string.timings_location_preference_key))
        bindPreferenceSummaryToValue(location)
        val methodType = findPreference(getString(R.string.timings_methodtype_preference_key))
        bindPreferenceSummaryToValue(methodType)
    }

    /**
     * Method for updating the summary of the preference to the summary area
     */
    private fun bindPreferenceSummaryToValue(preference: Preference?) {
        preference?.onPreferenceChangeListener = this
        val preferences = PreferenceManager.getDefaultSharedPreferences(ctx)
        val preferenceString = preferences.getString(preference?.key, "")
        onPreferenceChange(preference, preferenceString)
    }

    /**
     * updates the summary of a pref if the preference is a list Preference
     */
    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val stringValue = newValue.toString()
        if (preference is ListPreference) {
            val listPreference = preference
            val prefIndex = listPreference.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                val labels = listPreference.entries
                preference.setSummary(labels[prefIndex])
            } else {
                preference.summary = stringValue
            }
        }
        return true
    }

}
