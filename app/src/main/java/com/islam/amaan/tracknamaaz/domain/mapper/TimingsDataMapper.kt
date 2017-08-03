package com.islam.amaan.tracknamaaz.domain.mapper

import com.islam.amaan.tracknamaaz.data.Server.SalahHijriCalendar
import com.islam.amaan.tracknamaaz.data.Server.MuslimDay as DataMuslimDay
import com.islam.amaan.tracknamaaz.data.Server.SalahMonthCalendar
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriCalendar
import com.islam.amaan.tracknamaaz.domain.model.MuslimHijriMonth as ModelMuslimHijriMonth
import com.islam.amaan.tracknamaaz.domain.model.MuslimMonthCalendar
import com.islam.amaan.tracknamaaz.domain.model.MuslimDay as ModelMuslimDay
import com.islam.amaan.tracknamaaz.data.Server.MuslimHijriMonth

/**
 * Created by Amaan on 07-07-2017.
 */


class TimingsDataMapper {

    fun convertFromDataModel(salahMonthCalendar: SalahMonthCalendar): MuslimMonthCalendar {
        return MuslimMonthCalendar(
                MuslimDayList = convertMuslimDayListtoDomain(salahMonthCalendar.data))
    }

    private fun convertMuslimDayListtoDomain(list: List<DataMuslimDay>): List<ModelMuslimDay> {
        val mutableResultList = list.map { convertMuslimDayItemToDomain(it) }
        val resultList: List<ModelMuslimDay> = mutableResultList.toList()
        return resultList
    }

    private fun convertMuslimDayItemToDomain(item: DataMuslimDay): ModelMuslimDay {
        return ModelMuslimDay(namazDate = item.date.readable, imsak = item.timings.Imsak,
                fajr = item.timings.Fajr, sunrise = item.timings.Sunrise, dhuhr = item.timings.Dhuhr,
                asr = item.timings.Asr, sunset = item.timings.Sunset, maghrib = item.timings.Maghrib,
                isha = item.timings.Isha, midnight = item.timings.Midnight,
                unixTimeStamp = item.date.timestamp)
    }
}


class HijriDataMapper {

    fun convertFromDataModel(salahHijriCalendar: SalahHijriCalendar): MuslimHijriCalendar {
        return MuslimHijriCalendar(muslimHijriMonthList =
        convertMuslimHijriMonthListToDomain(salahHijriCalendar.data))
    }

    private fun convertMuslimHijriMonthListToDomain(list: List<MuslimHijriMonth>):
            List<ModelMuslimHijriMonth> {
        val mutableResultList = list.map { convertMuslimHijriMonthItemToDomain(it) }
        val resultList: List<ModelMuslimHijriMonth> = mutableResultList.toList()
        return resultList
    }

    private fun convertMuslimHijriMonthItemToDomain(item: MuslimHijriMonth):
            ModelMuslimHijriMonth {
        return ModelMuslimHijriMonth(hijriDate = item.hijri.date,
                gregorianDate = item.gregorian.date, hijriWeekDay = item.hijri.weekday.en,
                hijriMonth = item.hijri.month.en, gregorianMonth = item.gregorian.month.en,
                arabicHolidays = item.hijri.holidays)
    }
}