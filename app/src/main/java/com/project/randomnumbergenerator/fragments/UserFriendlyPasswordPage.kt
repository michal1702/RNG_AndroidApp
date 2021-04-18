package com.project.randomnumbergenerator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.databinding.FragmentUserFriendlyPasswordPageBinding
import com.project.randomnumbergenerator.interfaces.ToastManager
import com.project.randomnumbergenerator.model.UserFriendlyPasswordGenerator


/**
 * A simple [Fragment] subclass.
 */
class UserFriendlyPasswordPage(context: Context) : Fragment(), ToastManager{

    override val activityContext: Context = context
    private lateinit var binding: FragmentUserFriendlyPasswordPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserFriendlyPasswordPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wordsArray = Array(3) {""}
        binding.generateUserPasswordButton.setOnClickListener {
            var words: Int = 0
            if(!binding.userWord1TextEdit.text.isNullOrEmpty()) {
                wordsArray[0] = binding.userWord1TextEdit.text.toString()
                words++
            }
            if(!binding.userWord2TextEdit.text.isNullOrEmpty()) {
                wordsArray[1] = binding.userWord2TextEdit.text.toString()
                words++
            }
            if(!binding.userWord3TextEdit.text.isNullOrEmpty()) {
                wordsArray[2] = binding.userWord3TextEdit.text.toString()
                words++
            }

            if(words<2){
                longToast("Enter at least 2 words")
            }else {
                val passwordGenerator = UserFriendlyPasswordGenerator(wordsArray)
                binding.generatedUserPasswordTextView.text = passwordGenerator.getGeneratedPassword()
            }
        }
    }
}