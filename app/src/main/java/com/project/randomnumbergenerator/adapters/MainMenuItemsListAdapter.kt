package com.project.randomnumbergenerator.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.project.randomnumbergenerator.listitems.MainMenuListItem
import com.project.randomnumbergenerator.R

class MainMenuItemsListAdapter(private var context: Context, var arrayMainMenuListItem: ArrayList<MainMenuListItem>) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return arrayMainMenuListItem[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayMainMenuListItem.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = View.inflate(context, R.layout.main_menu_list_view_item,null)

        val itemIcon:ImageView = view.findViewById(R.id.item_icon)
        val itemTitle:TextView = view.findViewById(R.id.item_title)

        val mainMenuListItem: MainMenuListItem = arrayMainMenuListItem[position]
        itemIcon.setImageResource(mainMenuListItem.icons!!)
        itemTitle.text = mainMenuListItem.title

        return view
    }
}