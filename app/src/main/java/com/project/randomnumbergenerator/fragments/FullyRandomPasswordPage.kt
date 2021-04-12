package com.project.randomnumbergenerator.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.model.FullyRandomPasswordGenerator


class FullyRandomPasswordPage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fully_random_password_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val generateButton = view.findViewById<Button>(R.id.generateFullyRandomPasswordButton)
        val generatedPasswordTextView = view.findViewById<TextView>(R.id.fullyRandomPasswordTextView)
        val spinner = view.findViewById<Spinner>(R.id.passwordCharactersCountSpinner)
        val spinnerAdapter = ArrayAdapter.createFromResource(context!!,
                R.array.passwordCharactersCount,
                R.layout.spinner_layout
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        spinner.adapter = spinnerAdapter

        generateButton.setOnClickListener {
            val generator = FullyRandomPasswordGenerator(spinner.selectedItem.toString().toInt())
            generatedPasswordTextView.text = generator.getGeneratedPassword()
        }
    }

}