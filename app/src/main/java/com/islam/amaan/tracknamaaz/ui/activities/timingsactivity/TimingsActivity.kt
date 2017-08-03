package com.islam.amaan.tracknamaaz.ui.activities.timingsactivity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.islam.amaan.tracknamaaz.R
import com.islam.amaan.tracknamaaz.domain.commands.DomainTimingsRequest
import com.islam.amaan.tracknamaaz.domain.model.MuslimDay
import kotlinx.android.synthetic.main.activity_timings.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import java.text.DateFormat
import java.util.*


class TimingsActivity : AppCompatActivity() {

    val LOG_TAG = "TimingsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timings)
        setSupportActionBar(appToolbar)
        setupDate()
        tempButton.setOnClickListener { startActivity<temp>() }
    }

    private fun setupDate() {

        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        Log.d(LOG_TAG, "currentTime=$currentTime")
        val todayDate = convertTimeStampToDate(currentTime)
        Log.d(LOG_TAG, "dateFormat=$todayDate")
        dateTextView.text = todayDate
        getTiming("${calendar.get(Calendar.MONTH) + 1}",
                "${calendar.get(Calendar.YEAR)}", todayDate)
//        dateTextView.onClick {
//            val mYear = calendar.get(Calendar.YEAR)
//            val mMonth = calendar.get(Calendar.MONTH)
//            val mDay = calendar.get(Calendar.DAY_OF_MONTH)
//            DatePickerDialog(ctx,
//                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//                        requestDate = "$dayOfMonth-${monthOfYear + 1}-$year"
//                        val requestMonth = "${monthOfYear + 1}"
//                        val requestYear = "$year"
//                        val immutableDateWrapper = requestDate
//                        getTiming(requestMonth, requestYear, todayDate)
//                        dateTextView.text = immutableDateWrapper
//                    }, mYear, mMonth, mDay).show()
//        }

    }

    /**
     * Method to convert EpochTimeStamp in Milli Seconds to Current Date
     */
    private fun convertTimeStampToDate(localTime: Long): String {
        return DateFormat.getDateInstance().format(localTime)
    }

    /**
     * The Function responsible for making the httpRequest the api for and takes todays date and
     * month and year as a parameter
     * also calls update ui after the fetching of data from api completes
     */
    private fun getTiming(localMonth: String, localYear: String, todayDate: String) {
        doAsync {
            val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
            val methodType = prefs.getString(getString(R.string.timings_methodtype_preference_key),
                    "2")
            val latlon = prefs.getString(getString(R.string.timings_location_preference_key), "0,0")
            val school = prefs.getString(getString(R.string.timings_preference_school_of_thought_key),
                    "0")
            Log.d(LOG_TAG, "value of $latlon")
            val dtrObject = DomainTimingsRequest(latlon = latlon, month = localMonth,
                    year = localYear, methodType = methodType, school = school)
            val resultList = dtrObject.execute().MuslimDayList
            uiThread {
                updateUI(resultList, todayDate)
            }
        }
    }

    /**
     * Method to be run on MainThread updates the ui after the asyncTask is done with the loading
     * namaz timings it passes on a list and todays date for comparision with the date in model
     * class of MuslimDay
     */
    private fun updateUI(list: List<MuslimDay>, todayDate: String) {
        for (item in list) {
            val temp = convertTimeStampToDate(item.unixTimeStamp.toLong()*1000L)
            if (temp == todayDate) {
                val currentMuslimDay = item
                dateTextView.text = currentMuslimDay.namazDate
                fajrTimeTextView.text = currentMuslimDay.fajr
                sunriseTimeTextView.text = currentMuslimDay.sunrise
                zuhrTimeTextView.text = currentMuslimDay.dhuhr
                asrTimeTextView.text = currentMuslimDay.asr
                magribTimeTextView.text = currentMuslimDay.maghrib
                ishaTimeTextView.text = currentMuslimDay.isha
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.action_settings -> {
                startActivity(Intent(ctx, TimingsSettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
