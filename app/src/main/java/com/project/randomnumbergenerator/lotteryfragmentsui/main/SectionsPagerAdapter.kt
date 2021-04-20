package com.project.randomnumbergenerator.lotteryfragmentsui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.fragments.EuroJackpotFragment
import com.project.randomnumbergenerator.fragments.SixNumberLotteryFragment

private val TAB_TITLES = arrayOf(
        R.string.lottery_tab_1,
        R.string.lottery_tab_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1)
        return when(position){
            1 -> EuroJackpotFragment()
            2 -> SixNumberLotteryFragment()
            else -> PlaceholderFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}