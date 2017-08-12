package com.islam.amaan.tracknamaaz.ui.activities.timingsactivity

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.islam.amaan.tracknamaaz.R
import com.islam.amaan.tracknamaaz.domain.commands.DomainTimingsRequest
import com.islam.amaan.tracknamaaz.domain.model.MuslimDay
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriMonth
import com.islam.amaan.tracknamaaz.ui.adapters.HijriCalendarAdapter
import kotlinx.android.synthetic.main.activity_timings.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import java.text.DateFormat
import java.util.*

class temp : Fragment() {
    val LOG_TAG = "tempfrag"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.activity_timings,container,false)
        setupDate(rootView = rootView)
        return  rootView
    }
    private fun setupDate(rootView:View) {

        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        Log.d(LOG_TAG, "currentTime=$currentTime")
        val todayDate = convertTimeStampToDate(currentTime)
        Log.d(LOG_TAG, "dateFormat=$todayDate")
        rootView.dateTextView.text = todayDate
        getTiming("${calendar.get(Calendar.MONTH) + 1}",
                "${calendar.get(Calendar.YEAR)}", todayDate,rootView)
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
    private fun getTiming(localMonth: String, localYear: String, todayDate: String,rootView: View) {
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
                updateUI(resultList, todayDate,rootView = rootView)
            }
        }
    }

    /**
     * Method to be run on MainThread updates the ui after the asyncTask is done with the loading
     * namaz timings it passes on a list and todays date for comparision with the date in model
     * class of MuslimDay
     */
    private fun updateUI(list: List<MuslimDay>, todayDate: String,rootView: View) {
        for (item in list) {
            val temp = convertTimeStampToDate(item.unixTimeStamp.toLong()*1000L)
            if (temp == todayDate) {
                val currentMuslimDay = item
                rootView.dateTextView.text = currentMuslimDay.namazDate
                rootView.fajrTimeTextView.text = currentMuslimDay.fajr
                rootView.sunriseTimeTextView.text = currentMuslimDay.sunrise
                rootView.zuhrTimeTextView.text = currentMuslimDay.dhuhr
                rootView.asrTimeTextView.text = currentMuslimDay.asr
                rootView.magribTimeTextView.text = currentMuslimDay.maghrib
                rootView.ishaTimeTextView.text = currentMuslimDay.isha
            }
        }
    }


    private fun setuplisttemp(resultCalendar: List<MuslimHijriMonth>) {
        val hijriCalendarAdapter = HijriCalendarAdapter(ctx, resultCalendar)
        val listView = find<ListView>(R.id.list)
        listView.adapter = hijriCalendarAdapter
        listView.setOnItemClickListener {
            _, _, pos, _ ->
            Unit
            val muslimHijriMonth = resultCalendar[pos]
        }
    }
}
