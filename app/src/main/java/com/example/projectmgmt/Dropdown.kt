package com.example.projectmanagementapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmanagementapp.databinding.ActivityDropdownBinding
import java.util.*


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
            categoryModel.title = s
            categoryModel.isSelected = false
            categoryModelArrayList.add(categoryModel)
        }
        val myAdapter = MyAdapter(this@Dropdown, 0, categoryModelArrayList)
        spinner.adapter = myAdapter
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val category = sharedPreferences.all as Map<String,*>
        Toast.makeText(applicationContext, "" + category, Toast.LENGTH_LONG).show()

        val visibility: TextView = binding.varvisibletext


    }
}