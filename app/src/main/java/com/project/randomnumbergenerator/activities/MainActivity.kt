package com.project.randomnumbergenerator.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.project.randomnumbergenerator.*
import com.project.randomnumbergenerator.adapters.MainMenuItemsListAdapter
import com.project.randomnumbergenerator.interfaces.ToastManager
import com.project.randomnumbergenerator.listitems.MainMenuListItem

class MainActivity : AppCompatActivity(),AdapterView.OnItemClickListener, ToastManager {

    override val activityContext: Context = this
    private lateinit var listView: ListView
    private lateinit var mainMenuItemsListAdapter: MainMenuItemsListAdapter
    private var arrayMainMenuList:ArrayList<MainMenuListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Random Numbers Generator"

        listView = findViewById(R.id.listViewGenerators)
        arrayMainMenuList = ArrayList()
        arrayMainMenuList = setDataToArrayList()
        mainMenuItemsListAdapter = MainMenuItemsListAdapter(
                applicationContext,
                arrayMainMenuList!!
        )
        listView.adapter = mainMenuItemsListAdapter
        listView.onItemClickListener = this
    }

    private fun setDataToArrayList(): ArrayList<MainMenuListItem>{
        val arrayMainMenuList:ArrayList<MainMenuListItem> = ArrayList()
        arrayMainMenuList.add(
                MainMenuListItem(
                        R.drawable.ic_numbers,
                        "Random numbers"
                )
        )
        arrayMainMenuList.add(
                MainMenuListItem(
                        R.drawable.ic_word,
                        "Random words"
                )
        )
        arrayMainMenuList.add(
                MainMenuListItem(
                        R.drawable.ic_speaker,
                        "Random sounds"
                )
        )
        arrayMainMenuList.add(
                MainMenuListItem(
                        R.drawable.ic_password,
                        "Random password"
                )
        )
        arrayMainMenuList.add(
                MainMenuListItem(
                        R.drawable.ic_coin,
                        "Coin toss"
                )
        )
        arrayMainMenuList.add(
            MainMenuListItem(
                R.drawable.ic_lottery,
                "Lotto"
            )
        )
        return arrayMainMenuList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> {
                val rngIntent = Intent(this, RandomNumbersActivity::class.java)
                startActivity(rngIntent)
            }
            1 -> {
                val wordsIntent = Intent(this, RandomWordsActivity::class.java)
                startActivity(wordsIntent)
            }
            2 ->{
                val soundsIntent = Intent(this, RandomSoundsActivity::class.java)
                startActivity(soundsIntent)
            }
            3 ->{
                val passwordIntent = Intent(this, RandomPasswordActivity::class.java)
                startActivity(passwordIntent)
            }
            4 ->{
                val coinTossIntent = Intent(this, CoinTossActivity::class.java)
                startActivity(coinTossIntent)
            }
            5 ->{
                val lotteryIntent = Intent(this, LotteryActivity::class.java)
                startActivity(lotteryIntent)
            }
            else -> shortToast("Not implemented yet")
        }
    }
}