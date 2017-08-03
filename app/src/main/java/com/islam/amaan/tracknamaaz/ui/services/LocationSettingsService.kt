package com.islam.amaan.tracknamaaz.ui.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.islam.amaan.tracknamaaz.R
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast

/**
 * Created by Amaan on 19-07-2017.
 * The Service only uses FusedLocation Provider right now to get the Last known location
 * It'd be nice to have a not null mLastLocation in the Future by requesting the update directly
 */

class LocationSettingsService : Service() {


    //reference to the FusedLocationApiClient to be used for grabbing Location
    lateinit var mFusedLocationApiClient: FusedLocationProviderClient

    //LocationObject reference to hold the resultant location object
    var mLastLocation: Location? = null


    /**
     * companion object for Holding various useful Strings used for broadcasting and logging
     */
    companion object {
        val LOG_TAG = LocationSettingsService::class.java.name!!

        // used for giving the broadcast a unique name through out the app
        val ACTION_LOCATION_BROADCAST = LocationSettingsService::class.java.name + "LocationBroadcast"

        //identifiers for extra intent data lat and longitude
        val EXTRA_LATITUDE = "extra_latitude"
        val EXTRA_LONGITUDE = "extra_longitude"

    }


    /**
     *  Method is used to do the preparatory things such as adding Service init Log
     *  and setting GoogleApiClient and getting an instance of FusedLocationProviderClient.
     *
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand in Service")
        mFusedLocationApiClient = LocationServices.getFusedLocationProviderClient(ctx)
        getLastLocation()
        return super.onStartCommand(intent, flags, startId)
    }


    /**
     * The method is used to Send a Local broadcast using LocalBoradcast Manager and it broadcasts
     * the devices last known location
     */
    @Synchronized fun sendBroadcastMessage(location: Location) {
        val intent = Intent(ACTION_LOCATION_BROADCAST)
        intent.putExtra(EXTRA_LATITUDE, location.latitude)
        intent.putExtra(EXTRA_LONGITUDE, location.longitude)
        Log.d(LOG_TAG, " Location Broadcast Successful")
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent)
    }


    /**
     * The method is used to get the lastknownLocation task from the fusedLocationClient
     * results in a mLastLocation variable being assigned a location object.
     */


    @SuppressLint("MissingPermission")
    @Synchronized private fun getLastLocation() {
        //checking if location permission is given
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Log.d(LOG_TAG, "no Permissions Given")
            toast(resources.getString(R.string.location_settings_service_permission_denied_message))
        } else {
            mFusedLocationApiClient.lastLocation.addOnCompleteListener {
                task ->
                //following code can be used to ensure that mlastLocation is not null
                //otherwise the sendBroadcast Wont fire
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    Log.d(LOG_TAG, "mlastLocation has been assigned ")
                    sendBroadcastMessage(mLastLocation!!)
                } else {
                    Log.d(LOG_TAG, "lastLocation task resulted in null")
                    Toast.makeText(this,
                            resources.getString(R.string.location_settings_service_task_last_location_null),
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    /**
     * NOT NEEDED
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
