package com.project.randomnumbergenerator.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.project.randomnumbergenerator.listitems.ListItem
import com.project.randomnumbergenerator.R

class ItemsListAdapter(private var context: Context, var arrayListItem: ArrayList<ListItem>) : BaseAdapter() {

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
        val view:View = View.inflate(context, R.layout.list_view_item,null)

        val itemIcon:ImageView = view.findViewById(R.id.item_icon)
        val itemTitle:TextView = view.findViewById(R.id.item_title)

        val listItem: ListItem = arrayListItem[position]
        itemIcon.setImageResource(listItem.icons!!)
        itemTitle.text = listItem.title

        return view
    }
}