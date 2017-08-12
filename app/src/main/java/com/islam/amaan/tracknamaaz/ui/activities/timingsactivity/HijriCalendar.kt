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
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriMonth
import com.islam.amaan.tracknamaaz.ui.adapters.HijriCalendarAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find

/**
 * Created by Amaan on 30-07-2017.
 */

class HijriCalendar : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.listview_hijri_calendar, container, false)
        val LOG_TAG = HijriCalendar::class.java.simpleName
        val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
        val methodType = prefs.getString(getString(R.string.timings_methodtype_preference_key),
                "2")
        val latlon = prefs.getString(getString(R.string.timings_location_preference_key), "0,0")
        val school = prefs.getString(getString(R.string.timings_preference_school_of_thought_key),
                "0")
        val dtrObject = DomainTimingsRequest(latlon = latlon, month = "08",
                year = "2017", methodType = methodType, school = school)


        async(UI) {
            val result = bg { dtrObject.executeHijriCalendar().muslimHijriMonthList }
            setupHijriList(result.await())

        }

        Log.d(LOG_TAG, "Fired the oncreateView on Fragment ${rootView ?: "rootview = null"}")

        return rootView
    }


    private fun setupHijriList(resultCalendar: List<MuslimHijriMonth>) {
        val hijriCalendarAdapter = HijriCalendarAdapter(ctx, resultCalendar)
        val listView = find<ListView>(R.id.list)
        listView.adapter = hijriCalendarAdapter
//        listView.setOnItemClickListener {
//            _, _, pos, _ ->
//            val muslimHijriMonth = resultCalendar[pos]
//
//        }
    }
}