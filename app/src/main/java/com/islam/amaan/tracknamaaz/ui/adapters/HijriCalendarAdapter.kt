package com.islam.amaan.tracknamaaz.ui.adapters

import android.content.Context
import android.util.Log
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
 * ListView adapter that holds holds an arrayList of type MuslimHijriMonth and takes in the view
 * hijri_calendar_adpater_list_item
 */
class HijriCalendarAdapter(val ctx: Context, calendar: List<MuslimHijriMonth>) :
        ArrayAdapter<MuslimHijriMonth>(ctx, 0, calendar) {

    /**
     * The getView method handles the inflation of everysingle view in The list view
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(ctx)
                    .inflate(R.layout.hijri_calendar_adapter_list_item, parent, false)
        }
        val currentDay = getItem(position)

        //the view that handles the gregorian Date
        val gregorianDateTextView = listItemView?.find<TextView>(R.id.textView_hijri_calendar_gregorian)
        gregorianDateTextView?.text = currentDay?.gregorianDate

        //view to handle the hijri date
        val hijriTextView = listItemView?.find<TextView>(R.id.textView_hijri_calendar_hijri)
        hijriTextView?.text = currentDay?.hijriDate

        //view to handle the hijri weekday in arabic
        val weekDayTextView = listItemView?.find<TextView>(R.id.textView_hijri_calendar_hijri_weekday)
        weekDayTextView?.text = currentDay?.hijriWeekDay

        //TODO() make the current date list item highlight itself

        return listItemView
    }
}
