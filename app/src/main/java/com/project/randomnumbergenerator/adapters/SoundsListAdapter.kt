package com.project.randomnumbergenerator.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.model.SoundManager
import com.project.randomnumbergenerator.listitems.SoundsListItem

class SoundsListAdapter(var context: Context, var arraySoundsItems: ArrayList<SoundsListItem>):BaseAdapter() {
    private var isPlaying: Boolean = false
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
        val view:View = View.inflate(this.context, R.layout.random_sounds_list_view_item,null)
        val deleteButton: ImageView = view.findViewById(R.id.removeButtonImage)
        val playButton: ImageView = view.findViewById(R.id.playButtonImage)
        val soundName: TextView = view.findViewById(R.id.soundName)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarList)
        val player = SoundManager(this.context)
        soundName.text = this.arraySoundsItems[position].nameOfSound

        deleteButton.setOnClickListener {
            player.removeFile(this.arraySoundsItems[position].filename)
            this.arraySoundsItems.removeAt(position)
            notifyDataSetChanged()
        }

        playButton.setOnClickListener{
            if(!isPlaying) {
                isPlaying = true
                playButton.setImageResource(R.drawable.ic_skip)
                var currentPosition: Int
                player.playSound(this.arraySoundsItems[position].filename)
                Thread{
                    val duration = player.getDuration()
                    progressBar.max = duration
                    while(player.getCurrentPosition() < duration) {
                        currentPosition = player.getCurrentPosition()
                        progressBar.progress = currentPosition
                    }
                    playButton.setImageResource(R.drawable.ic_play)
                    progressBar.progress = 0
                }.start()
            }
            else{
                playButton.setImageResource(R.drawable.ic_play)
                player.skipSound()
                isPlaying = false
            }
        }
        return view
    }


}