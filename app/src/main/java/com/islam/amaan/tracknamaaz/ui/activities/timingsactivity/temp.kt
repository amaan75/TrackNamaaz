package com.islam.amaan.tracknamaaz.ui.activities.timingsactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ListView
import com.islam.amaan.tracknamaaz.R
import com.islam.amaan.tracknamaaz.domain.commands.DomainTimingsRequest
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriMonth
import com.islam.amaan.tracknamaaz.ui.adapters.HijriCalendarAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find

class temp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_temp)
        setContentView(R.layout.activity_temp)
        val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
        val methodType = prefs.getString(getString(R.string.timings_methodtype_preference_key),
                "2")
        val latlon = prefs.getString(getString(R.string.timings_location_preference_key), "0,0")
        val school = prefs.getString(getString(R.string.timings_preference_school_of_thought_key),
                "0")
        val dtrObject = DomainTimingsRequest(latlon = latlon, month = "08",
                year = "2017", methodType = methodType, school = school)

        async(UI) {
            val resultCalendar = bg { dtrObject.executeHijriCalendar().muslimHijriMonthList }
            setuplisttemp(resultCalendar.await())
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
