package com.project.randomnumbergenerator.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.databinding.RandomSoundsListViewItemBinding
import com.project.randomnumbergenerator.model.SoundManager
import com.project.randomnumbergenerator.listitems.SoundsListItem

class SoundsListAdapter(var context: Context, var arraySoundsItems: ArrayList<SoundsListItem>):BaseAdapter() {
    private var isPlaying: Boolean = false
    private var skip: Boolean = false
    private lateinit var binding: RandomSoundsListViewItemBinding

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
        binding = RandomSoundsListViewItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.soundName.text = this.arraySoundsItems[position].nameOfSound

        binding.removeButtonImage.setOnClickListener { deleteButtonClicked(position) }
        binding.playButtonImage.setOnClickListener { playButtonClicked(position) }
        return binding.root
    }

    private fun playButtonClicked(position: Int){
        val manager = SoundManager(this.context)
        if(!isPlaying) {
            isPlaying = true
            binding.playButtonImage.setImageResource(R.drawable.ic_skip)
            Thread{
                var currentPosition: Int
                manager.playSound(this.arraySoundsItems[position].filename)
                val duration = manager.getDuration()
                binding.progressBarList.max = duration
                while(manager.getCurrentPosition() < duration && !skip) {
                    currentPosition = manager.getCurrentPosition()
                    binding.progressBarList.progress = currentPosition
                }
                manager.skipSound()
                skip = false
                isPlaying = false
                binding.playButtonImage.setImageResource(R.drawable.ic_play)
                binding.progressBarList.progress = 0
            }.start()
        }
        else{
            binding.playButtonImage.setImageResource(R.drawable.ic_play)
            skip = true
            isPlaying = false
        }
    }

    private fun deleteButtonClicked(position: Int) {
        val player = SoundManager(this.context)
        player.removeFile(this.arraySoundsItems[position].filename)
        this.arraySoundsItems.removeAt(position)
        notifyDataSetChanged()
    }
}