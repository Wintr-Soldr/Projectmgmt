package com.example.projectmanagementapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmanagementapp.databinding.ActivityDropdownBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList


class Dropdown : AppCompatActivity() {
    private lateinit var binding: ActivityDropdownBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDropdownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val departments = resources.getStringArray(R.array.department)
        val adapter1 = ArrayAdapter(this, R.layout.dropdown_item1, departments)
        binding.autoCompleteTextView1.setAdapter(adapter1)

        val developers = resources.getStringArray(R.array.developers)
        val adapter2 = ArrayAdapter(this, R.layout.dropdown_item1, developers)
        binding.autoCompleteTextView2.setAdapter(adapter2)

        val spinner = findViewById<Spinner>(R.id.spinner1)
        val selectPeople = applicationContext.resources.getStringArray(R.array.listarray)
        val categoryModelArrayList = ArrayList<CategoryModel>()
        for (s in selectPeople) {
            val categoryModel = CategoryModel()
            categoryModel.setTitle(s)
            categoryModel.setSelected(false)
            categoryModelArrayList.add(categoryModel)
        }
        val myAdapter = MyAdapter(this@Dropdown, 0, categoryModelArrayList)
        spinner.adapter = myAdapter
        val sharedPreferences = this.getPreferences(MODE_PRIVATE)
        val category = sharedPreferences.all as Map<String, *>
        Toast.makeText(applicationContext, "" + category, Toast.LENGTH_LONG).show()

        val vis: TextView = binding.varvisibletext
        if (category.isEmpty()) {
            vis.visibility = View.VISIBLE
        } else {
            vis.visibility = View.GONE
        }

        val gridList: GridView = findViewById(R.id.grid_list)
        val values = ArrayList<String>()
        for(key in category.keys){
            values.add(key)
        }
        gridList.adapter = ArrayAdapter(this, R.layout.grid_item, values)
        val saveButton = findViewById<Button>(R.id.save_button)
        onButtonClick(saveButton)

    }
    fun onButtonClick(v: View?) {

        //get reference to TextView
        val textInputLayout = findViewById<TextInputLayout>(R.id.taskNameBox)
        val taskName: String = textInputLayout.editText!!.text.toString()
        val textInputLayoutmenu1 = findViewById<TextInputLayout>(R.id.menu1)
        val selectedValue1: String = (textInputLayoutmenu1.editText as AutoCompleteTextView?)!!.text.toString()

        val textInputLayoutmenu2 = findViewById<TextInputLayout>(R.id.menu2)
        val selectedValue2: String = (textInputLayoutmenu2.editText as AutoCompleteTextView?)!!.text.toString()

        //set up SharedPreferences
        val myPrefs: SharedPreferences = this.getSharedPreferences("prefID",MODE_PRIVATE)
        val editor: SharedPreferences.Editor = myPrefs.edit()
        editor.putString("taskName", taskName)
        editor.putString("departmentName", selectedValue1)
        editor.putString("developerType", selectedValue2)
        editor.apply()

        Toast.makeText(applicationContext,"Saved",Toast.LENGTH_SHORT).show()
    }
}
