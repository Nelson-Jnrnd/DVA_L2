package com.example.labo_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

    fun getCompany() : String {
        return requireView().findViewById<EditText>(R.id.main_specific_employee_editText_entreprise).text.toString()
    }

    fun getSector() : String {
        return requireView().findViewById<Spinner>(R.id.main_specific_employee_spinner_sector).selectedItem.toString()
    }

    fun getExperience() : Int {
        return requireView().findViewById<EditText>(R.id.main_specific_employee_editText_experience).text.toString().toInt()
    }

    fun validateForm(): Boolean {
        return try {
            val company = getCompany()
            val sector = getSector()
            val experience = getExperience()
            company.isNotEmpty() && sector.isNotEmpty() && experience > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EmployeeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            EmployeeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}