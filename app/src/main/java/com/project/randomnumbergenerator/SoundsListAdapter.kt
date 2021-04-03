package com.project.randomnumbergenerator

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class SoundsListAdapter(var context: Context, var arraySoundsItems: ArrayList<SoundsListItem>):BaseAdapter() {
    override fun getItem(position: Int): Any {
        return this.arraySoundsItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.arraySoundsItems.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = View.inflate(this.context,R.layout.random_sounds_list_view_item,null)
        val deleteButton: ImageView = view.findViewById(R.id.removeButtonImage)
        val playButton: ImageView = view.findViewById(R.id.playButtonImage)
        val soundName: TextView = view.findViewById(R.id.soundName)
        val player = SoundManager(this.context)
        soundName.text = this.arraySoundsItems[position].nameOfSound

        deleteButton.setOnClickListener {
            player.removeFile(this.arraySoundsItems[position].filename)
            this.arraySoundsItems.removeAt(position)
            notifyDataSetChanged()
        }

        playButton.setOnClickListener{
            player.playSound(this.arraySoundsItems[position].filename)
        }
        return view
    }


}