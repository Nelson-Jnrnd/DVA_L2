package com.example.labo_2

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ControllerActivity : AppCompatActivity() {

    private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set button listeners
        val birthdayBtn = findViewById<ImageButton>(R.id.main_base_button_birthdate)
        birthdayBtn.setOnClickListener {
            pickDate()
        }

        val cancelButton = findViewById<Button>(R.id.main_complement_button_cancel)
        cancelButton.setOnClickListener {
            clearForm(findViewById(R.id.main_activity_layout))
        }

        val okButton = findViewById<Button>(R.id.main_complement_button_ok)
        okButton.setOnClickListener {
            controlValidationForm()
        }

        val emailEdit = findViewById<EditText>(R.id.main_complement_editText_email)
        emailEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                controlValidationForm()
            }
            false
        }

        // Set radio button listeners
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

    private fun controlValidationForm() {
        if (validateForm()) {
            if (registerForm()) {
                clearForm(findViewById(R.id.main_activity_layout))
                Toast.makeText(this, "Person registered", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid Form", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickDate() {
        val birthday = findViewById<TextView>(R.id.main_base_textView_birthdate)

        val c = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
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

    private fun getStudentForm(): StudentFragment? {
        return supportFragmentManager.findFragmentByTag("studentForm") as? StudentFragment
    }

    private fun getEmployeeForm(): EmployeeFragment? {
        return supportFragmentManager.findFragmentByTag("employeeForm") as? EmployeeFragment
    }

    private fun clearForm(group: ViewGroup) {
        var i = 0
        val count = group.childCount
        while (i < count) {
            val view: View = group.getChildAt(i)
            if (view is EditText) {
                view.setText("")
            }
            if (view is ViewGroup && view.childCount > 0) clearForm(view)
            ++i
        }
    }


    private fun validateForm(): Boolean {
        val firstname = findViewById<EditText>(R.id.main_base_editText_firstname)
        val name = findViewById<EditText>(R.id.main_base_editText_name)
        val birthday = findViewById<TextView>(R.id.main_base_textView_birthdate)
        val nationality = findViewById<Spinner>(R.id.main_base_spinner_nationality)
        val email = findViewById<EditText>(R.id.main_complement_editText_email)
        val comments = findViewById<EditText>(R.id.main_complement_editText_comment)

        if(firstname.length() == 0 || name.length() == 0 || birthday.length() == 0
            || nationality.selectedItem.toString() == "Sélectionner" || email.length() == 0 || comments.length() == 0){
            return false
        }

        val occupation = findViewById<RadioGroup>(R.id.main_base_radioGroup)

        when (occupation.checkedRadioButtonId) {
            R.id.main_base_radioButton_student -> {
                val school = findViewById<EditText>(R.id.main_specific_student_editText_school)
                val yearDiploma = findViewById<EditText>(R.id.main_specific_student_editText_diploma)

                if (school.length() == 0 || yearDiploma.length() == 0) {
                    return false
                }
            }
            R.id.main_base_radioButton_employee -> {
                val entreprise = findViewById<EditText>(R.id.main_specific_employee_editText_entreprise)
                val experience = findViewById<EditText>(R.id.main_specific_employee_editText_experience)
                val sector = findViewById<Spinner>(R.id.main_specific_employee_spinner_sector)

                if (entreprise.length() == 0 || experience.length() == 0 || experience.length() == 0
                    || sector.selectedItem.toString() == "Sélectionner" ) {
                    return false
                }
            }
        }
        return true
    }


    private fun registerForm(): Boolean {
        val viewModel: PersonFormViewModel by viewModels()
        val firstname = findViewById<EditText>(R.id.main_base_editText_firstname)
        val name = findViewById<EditText>(R.id.main_base_editText_name)
        val birthday = findViewById<TextView>(R.id.main_base_textView_birthdate)
        val nationality = findViewById<Spinner>(R.id.main_base_spinner_nationality)
        val email = findViewById<EditText>(R.id.main_complement_editText_email)
        val comments = findViewById<EditText>(R.id.main_complement_editText_comment)

        // Checks which radio button is checked
        val occupation = findViewById<RadioGroup>(R.id.main_base_radioGroup)

        when (occupation.checkedRadioButtonId) {
            R.id.main_base_radioButton_student -> {
                val studentForm = getStudentForm()
                if (studentForm != null) {
                    val cal = Calendar.getInstance()
                    cal.time = simpleDateFormat.parse(birthday.text.toString())
                    viewModel.registerPerson(
                        Student(
                            name.text.toString(),
                            firstname.text.toString(),
                            cal,
                            nationality.selectedItem.toString(),
                            studentForm.getSchool(),
                            studentForm.getDiplomaYear(),
                            email.text.toString(),
                            comments.text.toString()
                        )
                    )
                    println(viewModel.uiState.value)
                    return true
                }
            }
            R.id.main_base_radioButton_employee -> {
                val employeeForm = getEmployeeForm()
                if (employeeForm != null) {
                    val cal = Calendar.getInstance()
                    cal.time = simpleDateFormat.parse(birthday.text.toString())
                    viewModel.registerPerson(
                        Worker(
                            name.text.toString(),
                            firstname.text.toString(),
                            cal,
                            nationality.selectedItem.toString(),
                            employeeForm.getCompany(),
                            employeeForm.getSector(),
                            employeeForm.getExperience(),
                            email.text.toString(),
                            comments.text.toString()
                        )
                    )
                    println(viewModel.uiState.value)
                    return true
                }
            }
        }
        return false
    }
}