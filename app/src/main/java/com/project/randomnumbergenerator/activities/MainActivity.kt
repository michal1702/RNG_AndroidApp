package com.project.randomnumbergenerator.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.project.randomnumbergenerator.*
import com.project.randomnumbergenerator.adapters.ItemsListAdapter
import com.project.randomnumbergenerator.listitems.ListItem

class MainActivity : AppCompatActivity(),AdapterView.OnItemClickListener {

    private lateinit var listView: ListView
    private lateinit var itemsListAdapter: ItemsListAdapter
    private var arrayList:ArrayList<ListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Random Numbers Generator"

        listView = findViewById(R.id.listViewGenerators)
        arrayList = ArrayList()
        arrayList = setDataToArrayList()
        itemsListAdapter = ItemsListAdapter(
                applicationContext,
                arrayList!!
        )
        listView.adapter = itemsListAdapter
        listView.onItemClickListener = this
    }

    private fun setDataToArrayList(): ArrayList<ListItem>{
        val arrayList:ArrayList<ListItem> = ArrayList()
        arrayList.add(
                ListItem(
                        R.drawable.ic_numbers,
                        "Random numbers"
                )
        )
        arrayList.add(
                ListItem(
                        R.drawable.ic_word,
                        "Random words"
                )
        )
        arrayList.add(
                ListItem(
                        R.drawable.ic_speaker,
                        "Random sounds"
                )
        )
        arrayList.add(
                ListItem(
                        R.drawable.ic_password,
                        "Random password"
                )
        )
        arrayList.add(
                ListItem(
                        R.drawable.temp,
                        "Lotto"
                )
        )
        arrayList.add(
                ListItem(
                        R.drawable.temp,
                        "Coin toss"
                )
        )
        arrayList.add(
                ListItem(
                        R.drawable.temp,
                        "Dices"
                )
        )
        return arrayList
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
                val passwordIntent  = Intent(this, RandomPasswordActivity::class.java)
                startActivity(passwordIntent)
            }
            else -> Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }
}