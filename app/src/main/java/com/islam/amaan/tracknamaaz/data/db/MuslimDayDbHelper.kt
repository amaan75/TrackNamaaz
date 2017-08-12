package com.islam.amaan.tracknamaaz.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.islam.amaan.tracknamaaz.ui.App
import org.jetbrains.anko.db.*

/**
 * Created by Amaan on 23-07-2017.
 * DB Helper class for storing db operations
 * 2 tables MuslimDay Table which stores the salah times and db hijriday table which stores
 * corresponding hijri dates and gregorain dates
 */
class MuslimDayDbHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        MuslimDayDbHelper.DB_NAME, null, MuslimDayDbHelper.DB_VERSION) {

    companion object {
        val DB_NAME = "salah.db"
        val DB_VERSION = 1
        val instance by lazy { MuslimDayDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MuslimDayTable.NAME, true,
                MuslimDayTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                MuslimDayTable.NAMAZDATE to TEXT,
                MuslimDayTable.IMSAK to TEXT,
                MuslimDayTable.FAJR to TEXT,
                MuslimDayTable.SUNRISE to TEXT,
                MuslimDayTable.DHUHR to TEXT,
                MuslimDayTable.ASR to TEXT,
                MuslimDayTable.MAGHRIB to TEXT,
                MuslimDayTable.SUNSET to TEXT,
                MuslimDayTable.ISHA to TEXT,
                MuslimDayTable.MIDNIGHT to TEXT,
                MuslimDayTable.UNIXTIMESTAMP to TEXT)

        db.createTable(HijriDayTable.NAME, true,
                HijriDayTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                HijriDayTable.HIJRIDATE to TEXT,
                HijriDayTable.GREGORIANDATE to TEXT,
                HijriDayTable.HIJRIWEEKDAY to TEXT,
                HijriDayTable.HIJRIMONTH to TEXT,
                HijriDayTable.GREGORIANMONTH to TEXT,
                HijriDayTable.ARABICHOLIDAYS to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MuslimDayTable.NAME, true)
        db.dropTable(HijriDayTable.NAME, true)
        onCreate(db)
    }
}