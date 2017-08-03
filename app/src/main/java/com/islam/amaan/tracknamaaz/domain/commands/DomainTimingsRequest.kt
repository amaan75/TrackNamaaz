package com.islam.amaan.tracknamaaz.domain.commands

import com.islam.amaan.tracknamaaz.data.Server.TimingsRequest
import com.islam.amaan.tracknamaaz.domain.mapper.HijriDataMapper
import com.islam.amaan.tracknamaaz.domain.mapper.TimingsDataMapper
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriCalendar
import com.islam.amaan.tracknamaaz.domain.model.MuslimMonthCalendar

/**
 * Created by Amaan on 07-07-2017.
 */


class DomainTimingsRequest(val latlon: String, val month: String, val year: String,
                           val methodType: String, val school: String) {

    val LOG_TAG = "Domain Timings Request"


    fun execute(): MuslimMonthCalendar {
        val resultList = TimingsRequest(latlon, month, year, methodType, school).execute()
        return TimingsDataMapper().convertFromDataModel(resultList)
    }

    fun executeHijriCalendar():MuslimHijriCalendar{
        val resultList = TimingsRequest(latlon,month,year,methodType,school).hijriRequest()
        return HijriDataMapper().convertFromDataModel(resultList)
    }

}