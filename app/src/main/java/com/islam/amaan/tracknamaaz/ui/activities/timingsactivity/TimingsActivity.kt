package com.islam.amaan.tracknamaaz.ui.activities.timingsactivity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.islam.amaan.tracknamaaz.R
import com.islam.amaan.tracknamaaz.domain.commands.DomainTimingsRequest
import com.islam.amaan.tracknamaaz.domain.model.MuslimDay
import com.islam.amaan.tracknamaaz.ui.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_timings.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.*
import java.text.DateFormat
import java.util.*


class TimingsActivity : AppCompatActivity() {

    val LOG_TAG = "TimingsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
    //    setSupportActionBar(appToolbar)

        val viewPager = find<ViewPager>(R.id.viewpager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

     }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.action_settings -> {
                startActivity(Intent(ctx, TimingsSettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
