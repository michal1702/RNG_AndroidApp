package com.project.randomnumbergenerator.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.databinding.FragmentUserFriendlyPasswordPageBinding
import com.project.randomnumbergenerator.interfaces.KeyboardHide
import com.project.randomnumbergenerator.interfaces.ToastManager
import com.project.randomnumbergenerator.model.UserFriendlyPasswordGenerator


/**
 * A simple [Fragment] subclass.
 */
class UserFriendlyPasswordPage(context: Context) : Fragment(), ToastManager, KeyboardHide{

    override val activity: Activity = context as Activity
    override val activityContext: Context = context
    private lateinit var binding: FragmentUserFriendlyPasswordPageBinding

    @ExperimentalStdlibApi
    private val generatePasswordButtonClickListener = View.OnClickListener { generatePasswordButtonClicked() }

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
        binding.passwordPageConstraintLayout.setOnClickListener { hideKeyboard() }
        binding.generateUserPasswordButton.setOnClickListener (generatePasswordButtonClickListener)
    }

    @ExperimentalStdlibApi
    private fun generatePasswordButtonClicked(){
        val wordsArray = Array(3) {""}
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