package com.islam.amaan.tracknamaaz.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.islam.amaan.tracknamaaz.R
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriMonth
import org.jetbrains.anko.find

/**
 * Created by Amaan on 30-07-2017.
 */
class HijriCalendarAdapter(val ctx: Context, calendar: List<MuslimHijriMonth>) :
        ArrayAdapter<MuslimHijriMonth>(ctx, 0, calendar) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(ctx).inflate(R.layout.hijri_calendar_adapter_list_item, parent, false)
        }
        val currentDay = getItem(position)

        val gregorianDateTextView = listItemView?.find<TextView>(R.id.textView_hijri_calendar_gregorian)
        gregorianDateTextView?.text = currentDay?.gregorianDate
        val hijriTextView = listItemView?.find<TextView>(R.id.textView_hijri_calendar_hijri)
        hijriTextView?.text = currentDay?.hijriDate

        val weekDayTextView = listItemView?.find<TextView>(R.id.textView_hijri_calendar_hijri_weekday)
        weekDayTextView?.text = currentDay?.hijriDate
        return listItemView
    }
}
