package com.islam.amaan.tracknamaaz.ui

import android.app.Application
import com.islam.amaan.tracknamaaz.extentions.DelegatesExt

/**
 * Created by Amaan on 23-07-2017.
 */
class App : Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
    }
}