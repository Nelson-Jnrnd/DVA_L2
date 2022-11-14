package com.example.labo_2

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

class ControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstname = findViewById<EditText>(R.id.main_base_editText_firstname)
        val name = findViewById<EditText>(R.id.main_base_editText_name)
        val birthdayBtn = findViewById<ImageButton>(R.id.main_base_button_birthdate)

        birthdayBtn.setOnClickListener {
            pickDate()
        }

        val viewModel : PersonFormViewModel by viewModels()

        viewModel.registerPerson(
            Student("John", "Doe", Calendar.getInstance(), "Suisse", "EPFL", 2020, "johndoe@mail.com","Computer Science")
        )

        lifecycleScope.launch {
            viewModel.uiState.collect {
                firstname.setText(it.person?.firstName)
                name.setText(it.person?.name)
            }
        }

        val student = findViewById<RadioButton>(R.id.main_base_radioButton_student)
        val employee = findViewById<RadioButton>(R.id.main_base_radioButton_employee)
        val fragmentContainerView = findViewById<FrameLayout>(R.id.fragmentContainerView)

        student.setOnClickListener {
            loadStudentForm(fragmentContainerView)
        }

        employee.setOnClickListener {
            loadEmployeeForm(fragmentContainerView)
        }


    }

    private fun pickDate() {
        val birthday = findViewById<TextView>(R.id.main_base_textView_birthdate)

        val c = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val pattern = "dd-MM-yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date = simpleDateFormat.format(calendar.time)
            birthday.text = date
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    private fun loadStudentForm(frameLayout: FrameLayout) {
        if (supportFragmentManager.findFragmentByTag("studentForm") == null) {
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, StudentFragment.newInstance(), "studentForm")
                .commit()
        }
    }

    private fun loadEmployeeForm(frameLayout: FrameLayout) {
        if (supportFragmentManager.findFragmentByTag("employeeForm") == null) {
            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, EmployeeFragment.newInstance(), "employeeForm")
                .commit()
        }
    }

}