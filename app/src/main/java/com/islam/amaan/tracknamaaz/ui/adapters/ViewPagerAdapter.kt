package com.islam.amaan.tracknamaaz.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.islam.amaan.tracknamaaz.ui.activities.timingsactivity.HijriCalendar
import com.islam.amaan.tracknamaaz.ui.activities.timingsactivity.temp

/**
 * Created by Amaan on 07-08-2017.
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("Numbers", "Colors", "Family", "Phrases")

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return HijriCalendar()
        else
            return temp()

    }

    override fun getCount(): Int {
        return 2
    }
}
