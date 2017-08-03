package com.islam.amaan.tracknamaaz.data.Server

import android.util.Log
import com.google.gson.Gson
import java.net.URL

/**
 * Created by Amaan on 03-07-2017.
 */
class TimingsRequest(val latlon: String, val month: String, val year: String,
                     val methodType: String, val school: String) {

    val LOG_TAG = "Timings Request"

    fun execute(): SalahMonthCalendar {
        val URL = "http://api.aladhan.com/calendar?$latlon" +
                "&method=$methodType&month=$month&year=$year&school=$school"
        Log.d(LOG_TAG, URL)
        val timeJsonStr = URL(URL).readText()
        Log.d(LOG_TAG, timeJsonStr)
        return Gson().fromJson(timeJsonStr, SalahMonthCalendar::class.java)
    }

    fun hijriRequest(): SalahHijriCalendar {
        val URL = "https://api.aladhan.com/gToHCalendar/$month/$year"
        Log.d(LOG_TAG, URL)
        val hijriJsonStr = URL(URL).readText()
        Log.d(LOG_TAG, hijriJsonStr)
        return Gson().fromJson(hijriJsonStr, SalahHijriCalendar::class.java)
    }
}