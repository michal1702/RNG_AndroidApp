package com.project.randomnumbergenerator

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class WordsListAdapter(private var context: Context, private var arrayListItem: ArrayList<String>): BaseAdapter() {
    override fun getItem(position: Int): Any {
        return arrayListItem[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayListItem.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = View.inflate(context,R.layout.random_words_list_view_item,null)
        val wordTextView: TextView = view.findViewById(R.id.wordNameTextView)
        val deleteButton: ImageView = view.findViewById(R.id.removeButtonImage)
        wordTextView.text = arrayListItem[position]

        deleteButton.setOnClickListener{
            this.arrayListItem.removeAt(position)
            notifyDataSetChanged()
        }
        return view
    }
}