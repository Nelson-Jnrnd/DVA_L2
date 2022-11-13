package com.example.labo_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Calendar

class ControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstname = findViewById<EditText>(R.id.main_base_editText_firstname)
        val name = findViewById<EditText>(R.id.main_base_editText_name)
        val occupation = findViewById<RadioGroup>(R.id.main_base_radioGroup)

        val nationalitiesSpinner = findViewById<Spinner>(R.id.main_base_spinner_nationality)
        nationalitiesSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.nationalities)
        )

        val viewModel : PersonFormViewModel by viewModels()

        viewModel.registerPerson(
            Student("John", "Doe", Calendar.getInstance(), "Suisse", "EPFL", 2020, "johndoe@mail.com","Computer Science")
        )

        lifecycleScope.launch {
            viewModel.uiState.collect {
                firstname.setText(it.person?.firstName)
                name.setText(it.person?.name)
                nationalitiesSpinner.setSelection(resources.getStringArray(R.array.nationalities).indexOf(it.person?.nationality))
            }
        }
    }

}