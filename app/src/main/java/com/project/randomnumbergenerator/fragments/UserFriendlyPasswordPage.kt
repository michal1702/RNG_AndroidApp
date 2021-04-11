package com.project.randomnumbergenerator.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.model.PasswordGenerator


/**
 * A simple [Fragment] subclass.
 */
class UserFriendlyPasswordPage : Fragment() {

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_user_friendly_password_page, container, false)
        val word1 = rootView.findViewById<EditText>(R.id.userWord1TextEdit)
        val word2 = rootView.findViewById<EditText>(R.id.userWord2TextEdit)
        val word3 = rootView.findViewById<EditText>(R.id.userWord3TextEdit)
        val generatePassword = rootView.findViewById<Button>(R.id.generateUserPasswordButton)
        val generatedPassword = rootView.findViewById<TextView>(R.id.generatedUserPasswordTextView)
        val wordsArray = Array(3) {""}
        generatePassword.setOnClickListener {
            var words: Int = 0
            if(!word1.text.isNullOrEmpty()) {
                wordsArray[0] = word1.text.toString()
                words++
            }
            if(!word2.text.isNullOrEmpty()) {
                wordsArray[1] = word2.text.toString()
                words++
            }
            if(!word3.text.isNullOrEmpty()) {
                wordsArray[2] = word3.text.toString()
                words++
            }

            if(words<2){
                Toast.makeText(context,"Enter at least 2 words",Toast.LENGTH_SHORT).show()
            }else {
                val passwordGenerator = PasswordGenerator()
                generatedPassword.text = passwordGenerator.getPasswordBasedOnWords(wordsArray)
            }
        }
        return rootView
    }

}