package com.islam.amaan.tracknamaaz.data.db

/**
 * Created by Amaan on 23-07-2017.
 */

/**
 * a single ton object for creation of a table containing all required coloumn names and other
 * as constant values
 */
object MuslimDayTable {
    val NAME = "MuslimDay"
    val ID = "_id"
    val NAMAZDATE = "namazdate"
    val IMSAK = "imsak"
    val FAJR = "fajr"
    val SUNRISE = "sunrise"
    val DHUHR = "dhuhr"
    val ASR = "asr"
    val SUNSET = "sunset"
    val MAGHRIB = "maghrib"
    val ISHA = "isha"
    val MIDNIGHT = "midnight"
    val UNIXTIMESTAMP = "unixtimestamp"
}


object HijriDayTable {
    val NAME = "HijriCalendar"
    val ID = "_id"
    val HIJRIDATE = "hijridate"
    val GREGORIANDATE = "gregoriandate"
    val HIJRIWEEKDAY = "hijriweekday"
    val HIJRIMONTH = "hijrimonth"
    val GREGORIANMONTH = "gregorianmonth"
    val ARABICHOLIDAYS = "arabicholidays"

}