package com.project.randomnumbergenerator.activities

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.passwordfragmentsui.main.SectionsPagerAdapter

class RandomPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_password)
        modifyActionBar()
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    /**
     * Changes style of action bar
     */
    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random password"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}