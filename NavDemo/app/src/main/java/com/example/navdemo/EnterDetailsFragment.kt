package com.example.navdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class EnterDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_enter_details, container, false)

        val etName = rootView.findViewById<EditText>(R.id.et_first_name)
        val etMobileNumber = rootView.findViewById<EditText>(R.id.et_mobile)

        val btnVerifyDetails = rootView.findViewById<Button>(R.id.btn_verify_details)
        btnVerifyDetails.setOnClickListener {

            val firstName = etName.text.toString()
            val mobile = etMobileNumber.text.toString()

            when {
                firstName.isEmpty() -> {
                    Toast.makeText(activity, "Enter Name", Toast.LENGTH_SHORT).show()
                }

                mobile.isEmpty() -> {
                    Toast.makeText(activity, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
        return rootView
    }
}