package com.example.projectmanagementapp

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*


class MyAdapter(private val mContext: Context, resource: Int, objects: List<CategoryModel>) :
    ArrayAdapter<CategoryModel?>(mContext, resource, objects) {
    private val listState: ArrayList<CategoryModel>
    private val myAdapter: MyAdapter
    private var isFromView = false

    override fun isEnabled(position : Int): Boolean {
        return position != 0
    }
    override fun getDropDownView(
        position: Int, convertView: View,
        parent: ViewGroup
    ): View {
        val view = super.getDropDownView(position, convertView, parent)
        val tv = view as TextView
        if (position == 0) {
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY)
        } else {
            tv.setTextColor(Color.BLACK)
        }
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView!!, parent)
    }

    fun getCustomView(
        position: Int, convertView: View,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            val layoutInflator = LayoutInflater.from(mContext)
            convertView = layoutInflator.inflate(R.layout.dorpdown_checkbox_item, null)
            holder = ViewHolder()
            holder.mTextView = convertView
                .findViewById<View>(R.id.text) as TextView
            holder.mCheckBox = convertView
                .findViewById<View>(R.id.checkbox) as CheckBox
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.mTextView!!.text = listState[position].title
        // To check weather checked event fire from getview() or user input
        isFromView = true
        holder.mCheckBox!!.isChecked = listState[position].isSelected
        isFromView = false
        if (position == 0) {
            holder.mCheckBox!!.visibility = View.INVISIBLE
        } else {
            holder.mCheckBox!!.visibility = View.VISIBLE
        }
        holder.mCheckBox!!.tag = position
        holder.mCheckBox!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val getPosition = buttonView.tag as Int
            if (!isFromView) {
                listState[position].isSelected = isChecked
                Log.i("checkpos", "" + listState[position].title)
                Log.i("valueif", "check$isFromView")
                Toast.makeText(context, "" + listState[position].isSelected, Toast.LENGTH_LONG)
                    .show()
                if (listState[position].isSelected) {
                    Log.i("showStatus", "You are beast")
                    val sharedPreferences =
                        mContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(listState[position].title, listState[position].title)
                    editor.apply()
                } else {
                    Log.i("showStatus", "You are Nut")
                    val sharedPreferences =
                        mContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.remove(listState[position].title)
                    editor.apply()
                }
            }
        }
        return convertView
    }

    private inner class ViewHolder {
        var mTextView: TextView? = null
        var mCheckBox: CheckBox? = null
    }

    init {
        listState = objects as ArrayList<CategoryModel>
        myAdapter = this
    }
}
