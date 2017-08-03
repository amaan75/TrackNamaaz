package com.islam.amaan.tracknamaaz.ui.activities.timingsactivity.locationpreferences

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.preference.DialogPreference
import android.support.v4.content.LocalBroadcastManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.islam.amaan.tracknamaaz.R
import com.islam.amaan.tracknamaaz.ui.services.LocationSettingsService
import org.jetbrains.anko.find

/**
 * Created by Amaan on 17-07-2017.
 */

class LocationPreference(ctx: Context, attrs: AttributeSet) :
        DialogPreference(ctx, attrs) {

    val LOG_TAG = LocationPreference::class.java.name!!

    init {
        dialogLayoutResource = R.layout.location_preference
        positiveButtonText = ctx.resources.getString(R.string.custom_dialog_positive_text)
        negativeButtonText = ctx.resources.getString(R.string.custom_dialog_negative_text)
    }

    var locationString = "0,0"
        set(value) {
            field = value
            persistString(field)
            Log.d(LOG_TAG,"while changing value in Location pref $field")
        }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult)
            persistString(locationString)
        Log.d(LOG_TAG," on dialog closed $locationString ")
    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)
        val gpsButton = view!!.find<ImageButton>(R.id.gpsButton)
        val gpsCoordinatesTextView = view.find<TextView>(R.id.gpsCoordinates)
       gpsCoordinatesTextView.text=preferenceManager.sharedPreferences
               .getString(context.getString(R.string.timings_location_preference_key),"0,0")
        gpsButton.setOnClickListener {
            Log.d(LOG_TAG, "clicked gps Image button")
            context.startService(Intent(context, LocationSettingsService::class.java))
            LocalBroadcastManager.getInstance(context).registerReceiver(
                    object : BroadcastReceiver() {
                        override fun onReceive(context: Context, intent: Intent) {
                            val latitude = intent.getDoubleExtra(LocationSettingsService.EXTRA_LATITUDE,
                                    0.0)
                            val longitude = intent.getDoubleExtra(LocationSettingsService.EXTRA_LONGITUDE,
                                    0.0)
                            Log.d(LOG_TAG, "Coordinates received Broadcast Received")
                            gpsCoordinatesTextView.text = "Lat: $latitude,Lng: $longitude"
                            locationString = "latitude=$latitude&longitude=$longitude"
                            context.stopService(Intent(context, LocationSettingsService::class.java))
                        }
                    }, IntentFilter(LocationSettingsService.ACTION_LOCATION_BROADCAST)
            )
        }


    }
}
