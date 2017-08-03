package com.islam.amaan.tracknamaaz.domain.model

/**
 * Created by Amaan on 07-07-2017.
 */


data class MuslimMonthCalendar(val MuslimDayList:List<MuslimDay>)


/**
 * BaseModel for a single days worth of timings for salah
 */
data class MuslimDay(val namazDate: String,val imsak:String,val fajr: String, val sunrise: String,
                     val dhuhr: String, val asr: String, val sunset:String, val maghrib: String,
                     val isha: String, val midnight:String,val unixTimeStamp:String)


/**
 * Classes needed for Hijri Calendar
 */

data class MuslimHijriCalendar(val muslimHijriMonthList:List<MuslimHijriMonth>)

data class MuslimHijriMonth(val hijriDate:String,val gregorianDate:String,val hijriWeekDay:String,
                            val hijriMonth:String,val gregorianMonth:String,
                            val arabicHolidays:List<String?>?)

