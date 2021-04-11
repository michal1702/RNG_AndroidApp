package com.project.randomnumbergenerator.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.ui.main.SectionsPagerAdapter

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

    /**
     * Method hides keyboard on focus off
     * @param view view of this activity
     */
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}