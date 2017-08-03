package com.islam.amaan.tracknamaaz.data.Server

/**
 * Created by Amaan on 03-07-2017.
 * Classes required by Gson Library for for producing an output from alAdhaan api
 */


data class SalahMonthCalendar(val code:Int, val status:String, val data:List<MuslimDay>)

/**
 * class required for holding arrays of date and timings object
 */
data class MuslimDay(val date: MuslimDayDate, val timings: MuslimDayTimings)

/**
 * class required for a single timings object from data object arrays
 */
data class MuslimDayTimings(val Imsak:String,val Fajr: String, val Sunrise: String, val Dhuhr: String,
                     val Asr: String, val Sunset:String, val Maghrib: String,val Isha: String,
                     val Midnight:String)

/**
 * class required for holding a single date object from data object arrays
 * readable string is the readable format of unix timestamp
 */
data class MuslimDayDate(val readable:String,val timestamp:String)


/**
 * class needed to store the hijri calendar and the gregorian dates
 */
data class SalahHijriCalendar(val code: Int,val status: String,val data: List<MuslimHijriMonth>)

/**
 * class to store a single hijriDay and gregorian Day
 */
data class MuslimHijriMonth(val hijri:HijriDay, val gregorian:GregorianDay)

data class HijriDay(val date:String,val day:String,val weekday:HijriWeekDay,
                    val month:HijriMonthDetails, val year:String,val holidays:List<String>)

data class HijriWeekDay(val en:String,val ar:String)

data class HijriMonthDetails(val number:Int,val en:String,val ar:String)


data class GregorianDay(val date: String,val day:String, val weekday:GregorianWeekDay,
                        val month:GregorianMonth,val year: String)

data class GregorianWeekDay(val en:String)

data class GregorianMonth(val number: Int,val en:String)
