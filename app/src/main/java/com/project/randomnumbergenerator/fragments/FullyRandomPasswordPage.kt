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
import com.project.randomnumbergenerator.databinding.FragmentFullyRandomPasswordPageBinding
import com.project.randomnumbergenerator.model.FullyRandomPasswordGenerator


class FullyRandomPasswordPage : Fragment() {

    private lateinit var binding: FragmentFullyRandomPasswordPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFullyRandomPasswordPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerAdapter = ArrayAdapter.createFromResource(context!!,
                R.array.passwordCharactersCount,
                R.layout.spinner_layout
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binding.passwordCharactersCountSpinner.adapter = spinnerAdapter

        binding.generateFullyRandomPasswordButton.setOnClickListener {
            val generator = FullyRandomPasswordGenerator(binding.passwordCharactersCountSpinner.selectedItem.toString().toInt())
            binding.fullyRandomPasswordTextView.text = generator.getGeneratedPassword()
        }
    }

}