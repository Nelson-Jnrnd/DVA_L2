package com.example.labo_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner


/**
 * A simple [Fragment] subclass.
 * Use the [StudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    fun getSchool(): String {
        return requireView().findViewById<EditText>(R.id.main_specific_student_editText_school).text.toString()
    }

    fun getDiplomaYear(): Int {
        val year =
            requireView().findViewById<EditText>(R.id.main_specific_student_editText_diploma).text.toString()
        try {
            return year.toInt()
        } catch (e: NumberFormatException) {
            throw NumberFormatException("Invalid year")
        }
    }

    fun validateForm(): Boolean {
        return try {
            val school = getSchool()
            val diplomaYear = getDiplomaYear()
            school.isNotEmpty() && diplomaYear > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment StudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            StudentFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}